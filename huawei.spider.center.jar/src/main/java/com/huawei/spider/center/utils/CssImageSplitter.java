package com.huawei.spider.center.utils;

import com.steadystate.css.dom.CSSStyleDeclarationImpl;
import com.steadystate.css.dom.CSSStyleRuleImpl;
import com.steadystate.css.dom.CSSStyleSheetImpl;
import com.steadystate.css.format.CSSFormat;
import com.steadystate.css.parser.CSSOMParser;
import com.steadystate.css.parser.SACParserCSS3;
import org.w3c.css.sac.InputSource;
import org.w3c.dom.css.*;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Processes .css files, separating out inlined background images (background-image: url(data:image/...))
 * into another file, so they can be loaded separately (presumably on document load).
 * <p>
 * Created by emurphy on 9/25/15.
 */
public final class CssImageSplitter {
    private final Log log;

    final static Pattern inlineImagePattern = Pattern.compile("data\\s*:\\s*image");

    /**
     * Simplified logging interface.
     * Callers should create an adapter for this interface.
     */
    public interface Log {
        void info(CharSequence var1);
    }

    /**
     * internal default implementation of simplified log
     */
    public static class NullLog implements Log {
        public void info(CharSequence var1) {
            // default is no logging
        }
    }

    public static class DefaultLog implements Log {
        public void info(CharSequence var1) {
            System.out.println(var1);
        }
    }

    public CssImageSplitter() {
        this.log = new DefaultLog();
    }

    public CssImageSplitter(Log theLog) {
        this.log = (null != theLog) ? theLog : new NullLog();
    }

    /**
     * Recursively walk folders and process css files.
     * Processes .css files, separating out inlined background images (background-image: url(data:image/...))
     * into another file, so they can be loaded separately (presumably on document load).
     *
     * @param theSourceFolder
     * @param theDestinationFolder
     * @param theMainSuffix
     * @param theImagesSuffix
     */
    public void splitCssImagesFolder(final String theSourceFolder, final String theDestinationFolder, final String theMainSuffix, final String theImagesSuffix) {
        splitCssImagesFolder(
                Paths.get(theSourceFolder),
                ((null == theDestinationFolder) || theDestinationFolder.isEmpty())
                        ? Paths.get(theSourceFolder)
                        : Paths.get(theDestinationFolder),
                theMainSuffix,
                theImagesSuffix);
    }

    /**
     * Recursively walk folders and process css files.
     * Processes .css files, separating out inlined background images (background-image: url(data:image/...))
     * into another file, so they can be loaded separately (presumably on document load).
     *
     * @param theSourceFolderPath
     * @param theDestinationFolderPath
     * @param theMainSuffix
     * @param theImagesSuffix
     */
    public void splitCssImagesFolder(final Path theSourceFolderPath, final Path theDestinationFolderPath, final String theMainSuffix, final String theImagesSuffix) {
        //
        // recursively search theSourceFolder for .css files.
        // - process each .css file, producing two new .css files
        //   - one (images) file with inlined background-image styles
        //   - one (main) file with all other styles
        //
        // NOTE: Files.newDirectoryStream() is lazy, so if the source and destination
        //       folders are the same, we may pickup the files that we just created.
        //       That can cause an infinite loop.  So we eagerly build the list
        //       of path entries, then process this list to avoid the issue.
        //
        final List<Path> theDirectoryContents = new LinkedList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(theSourceFolderPath)) {
            for (Path entry : stream) {
                theDirectoryContents.add(entry);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Path entry : theDirectoryContents) {
            if (Files.isDirectory(entry)) {
                //
                // recursive call to process a sub-directory.
                // - add the source sub-directory to the destination path
                //   to maintain the directory structure in the destination folders
                //
                splitCssImagesFolder(entry, theDestinationFolderPath.resolve(entry.getFileName()), theMainSuffix, theImagesSuffix);
            } else // it is a file
            {
                // if it is a css file, process it
                final String[] theFileNameParts = entry.getFileName().toString().split("\\.");
                if ((theFileNameParts.length > 1) && ("css".equalsIgnoreCase(theFileNameParts[theFileNameParts.length - 1]))) {
                    splitCssImagesFile(entry.toString(), theDestinationFolderPath.toString(), theMainSuffix, theImagesSuffix);
                }
            }
        }
    }

    /**
     * Processes one .css file, separating out inlined background images (background-image: url(data:image/...))
     * into another file, so they can be loaded separately (presumably on document load).
     *
     * @param theCssFilePath
     * @param theDestinationFolder
     * @param theMainSuffix
     * @param theImagesSuffix
     */
    public void splitCssImagesFile(final String theCssFilePath, final String theDestinationFolder, final String theMainSuffix, final String theImagesSuffix) {
        if ((null == theDestinationFolder) || theDestinationFolder.isEmpty()) {
            if ((null == theMainSuffix) || theMainSuffix.isEmpty()) {
                if ((null == theImagesSuffix) || theImagesSuffix.isEmpty()) {
                    return; // the source and destination will be the same, so no need to do any processing.
                }
            }
        }

        log.info("Splitting " + theCssFilePath);
        try (final InputStream inStream = new FileInputStream(theCssFilePath)) {
            final InputSource source = new InputSource(new InputStreamReader(inStream, "UTF-8"));
            final CSSOMParser parser = new CSSOMParser(new SACParserCSS3());
            final CSSStyleSheet theInputSheet = parser.parseStyleSheet(source, null, null);
            final CSSStyleSheetImpl theOutputSheet = new CSSStyleSheetImpl();
            final CSSStyleSheetImpl theImageOutputSheet = new CSSStyleSheetImpl();

            //
            // print all rules
            //
            CSSRuleList rules = theInputSheet.getCssRules();
//                for (int i = 0; i < rules.getLength(); i++)
//                {
//                    final CSSRule rule = rules.item(i);
//
//                    System.out.println(rule.getCssText());
//                }

            int theImageOutputCount = 0;
            for (int i = 0; i < rules.getLength(); i++) {
                final CSSRule rule = rules.item(i);
                if (CSSRule.STYLE_RULE == rule.getType()) {
                    //
                    // the rule includes a set of style declarations.
                    // each style declaration includes a property, a value, and an importance.
                    //
                    // copy all background-image styles to the image rule.
                    // copy all other styles to the main output rule.
                    //
                    final CSSStyleDeclaration theOutputStyles = new CSSStyleDeclarationImpl();
                    final CSSStyleDeclaration theImageStyles = new CSSStyleDeclarationImpl();
                    final CSSStyleRuleImpl theCSSStyleRule = (CSSStyleRuleImpl) rule;
                    final CSSStyleDeclaration theCSSStyleDeclaration = theCSSStyleRule.getStyle();
                    for (int j = 0; j < theCSSStyleDeclaration.getLength(); j += 1) {
                        final String thePropertyName = theCSSStyleDeclaration.item(j);
                        final CSSValue theCSSValue = theCSSStyleDeclaration.getPropertyCSSValue(thePropertyName);
                        final String thePriority = theCSSStyleDeclaration.getPropertyPriority(thePropertyName);
                        if ("background-image".equals(thePropertyName) && (null != theCSSValue)) {
                            if (CSSValue.CSS_PRIMITIVE_VALUE == theCSSValue.getCssValueType()) {
                                //
                                // if the background-image uses a data url (has "data:image" as start of url
                                // then put this in the image styles.  Otherwise in the main output styles.
                                //
                                final CSSPrimitiveValue theCSSPrimitiveValue = (CSSPrimitiveValue) theCSSValue;
                                if ((CSSPrimitiveValue.CSS_URI == theCSSPrimitiveValue.getPrimitiveType())
                                        && (inlineImagePattern.matcher(theCSSPrimitiveValue.getStringValue()).find()))
                                //&& (theCSSPrimitiveValue.getStringValue().contains("data:image")))
                                {
                                    //
                                    // output this to the image css file
                                    //
                                    final String theUriText = theCSSPrimitiveValue.getStringValue();
                                    theImageStyles.setProperty(
                                            "background-image",
                                            theCSSValue.toString(),
                                            thePriority);
                                    theImageOutputCount += 1;
                                } else {
                                    // pass this through to the main css file
                                    theOutputStyles.setProperty(
                                            "background-image",
                                            theCSSValue.toString(),
                                            theCSSStyleDeclaration.getPropertyPriority("background-image"));
                                }
                            } else {
                                // pass this through to the main css file
                                // pass this through to the main css file
                                theOutputStyles.setProperty(
                                        "background-image",
                                        theCSSValue.toString(),
                                        theCSSStyleDeclaration.getPropertyPriority("background-image"));
                            }
                        } else {
                            // the rule is not a background-image rule, so output the main file
                            // pass this through to the main css file
                            theOutputStyles.setProperty(
                                    thePropertyName,
                                    theCSSValue.toString(),
                                    theCSSStyleDeclaration.getPropertyPriority("background-image"));
                        }
                    }

                    //
                    // copy non-background-image styles to main output
                    //
                    if (!theOutputStyles.getCssText().isEmpty()) {
                        final CSSStyleRuleImpl theOutputStyleRule =
                                new CSSStyleRuleImpl(
                                        (CSSStyleSheetImpl) theCSSStyleRule.getParentStyleSheet(),
                                        theCSSStyleRule.getParentRule(),
                                        theCSSStyleRule.getSelectors());
                        theOutputStyleRule.setStyle(theOutputStyles);
                        theOutputSheet.insertRule(theOutputStyleRule.getCssText(), theOutputSheet.getCssRules().getLength());
                    }

                    //
                    // copy background-image styles to image output
                    //
                    if (!theImageStyles.getCssText().isEmpty()) {
                        final CSSStyleRuleImpl theStyleRule =
                                new CSSStyleRuleImpl(
                                        (CSSStyleSheetImpl) theCSSStyleRule.getParentStyleSheet(),
                                        theCSSStyleRule.getParentRule(),
                                        theCSSStyleRule.getSelectors());
                        theStyleRule.setStyle(theImageStyles);
                        theImageOutputSheet.insertRule(theStyleRule.getCssText(), theImageOutputSheet.getCssRules().getLength());
                    }
                } else // not a style rule
                {
                    // pass this into the main output file
                    theOutputSheet.insertRule(rule.getCssText(), theOutputSheet.getCssRules().getLength());
                }
            }

            //
            // if there were any images to output, the we need to write both the main and image files.
            // otherwise we can just leave the original file as is.
            //
            if (theImageOutputCount > 0) {
                /*final CSSFormat format = new CSSFormat();
                final CssFormatter theCssFormatter = new CssFormatter();

                //
                // output the file without the background-image
                //
                StringBuilder theOutputText = new StringBuilder();
                theCssFormatter.formatCSSStyleSheet(theOutputSheet, theOutputText);
                writeStringToFile(convertToOutputPath(theCssFilePath, theDestinationFolder, theMainSuffix), theOutputText.toString());
                // System.out.println(theOutputText.toString());      // may be empty

                //
                // output the file with the background-image
                //
                theOutputText = new StringBuilder();
                theCssFormatter.formatCSSStyleSheet(theImageOutputSheet, theOutputText);
                writeStringToFile(convertToOutputPath(theCssFilePath, theDestinationFolder, theImagesSuffix), theOutputText.toString());*/
                // System.out.println(theOutputText.toString()); // should only have background-image styles
            }

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeStringToFile(final Path theFilePath, final String theString) {
        // make sure the folder exist before writing the file
        makeFolder(theFilePath.getParent());
        log.info("Writing $1".replace("$1", theFilePath.toString()));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(theFilePath.toFile()))) {
            writer.write(theString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void makeFolder(final Path theFolderPath) {
        final File theFolder = theFolderPath.toFile();
        if (!theFolder.exists()) {
            log.info("$1 does not exist, creating it.".replace("$1", theFolderPath.toString()));
            if (!theFolder.mkdirs()) {
                log.info("Failed creating folder $1".replace("$1", theFolderPath.toString()));
            }
        }
    }

    private Path convertToOutputPath(final String theInputFilePath, final String theOutputFolderPath, final String theOutputSuffix) {
        //
        // if there is no path transform (no output folder, no suffix), just return the given path
        //
        if (((null == theOutputSuffix) || theOutputSuffix.isEmpty())
                && ((null == theOutputFolderPath) || theOutputFolderPath.isEmpty())) {
            return Paths.get(theInputFilePath);
        }

        //
        // convert input path to output path and add suffix to the filename
        //
        final Path theFilePath = Paths.get(theInputFilePath);
        final String theOutputPath =
                ((null != theOutputFolderPath) && !theOutputFolderPath.isEmpty())
                        ? theOutputFolderPath
                        : theFilePath.getParent().toString();

        //
        // separate the extension from the file name so we can add the suffix
        //
        final String[] theFileNameParts = theFilePath.getFileName().toString().split("\\.");
        if (theFileNameParts.length > 1) {
            //
            // output path + base file name + suffix + extension
            //
            final StringBuilder theBuilder = new StringBuilder();
            theBuilder.append(theFileNameParts[0]);
            for (int i = 1; i < theFileNameParts.length - 1; i += 1) {
                theBuilder.append('.').append(theFileNameParts[i]);
            }
            theBuilder.append((null != theOutputSuffix) ? theOutputSuffix : "");
            theBuilder.append('.');
            theBuilder.append(theFileNameParts[theFileNameParts.length - 1]);
            return Paths.get(theOutputPath, theBuilder.toString());
        }

        //
        // no file extension, we can just add the suffix directly to the path
        //
        return Paths.get(theOutputPath, theFileNameParts[0] + ((null != theOutputSuffix) ? theOutputSuffix : ""));
    }
}
