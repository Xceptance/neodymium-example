package posters.gifrecording;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;

public class GifSequenceWriter implements AutoCloseable
{

    protected ImageWriter writer;

    protected ImageWriteParam params;

    protected IIOMetadata metadata;

    private double imageQuality;

    private double imageScaleFactor;

    public GifSequenceWriter(ImageOutputStream out, int imageType, int oneImagePerMilliseconds, double imageQuality,
        double imageScaleFactor, boolean loop) throws IOException
    {
        this.imageQuality = imageQuality;
        this.imageScaleFactor = imageScaleFactor;

        writer = ImageIO.getImageWritersBySuffix("gif").next();
        params = writer.getDefaultWriteParam();

        ImageTypeSpecifier imageTypeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(imageType);
        metadata = writer.getDefaultImageMetadata(imageTypeSpecifier, params);

        configureRootMetadata(oneImagePerMilliseconds, loop);

        writer.setOutput(out);
        writer.prepareWriteSequence(null);
    }

    private void configureRootMetadata(int delay, boolean loop) throws IIOInvalidTreeException
    {
        String metaFormatName = metadata.getNativeMetadataFormatName();
        IIOMetadataNode root = (IIOMetadataNode) metadata.getAsTree(metaFormatName);

        IIOMetadataNode graphicsControlExtensionNode = getNode(root, "GraphicControlExtension");
        graphicsControlExtensionNode.setAttribute("disposalMethod", "none");
        graphicsControlExtensionNode.setAttribute("userInputFlag", "FALSE");
        graphicsControlExtensionNode.setAttribute("transparentColorFlag", "FALSE");
        graphicsControlExtensionNode.setAttribute("delayTime", Integer.toString(delay / 10));
        graphicsControlExtensionNode.setAttribute("transparentColorIndex", "0");

        IIOMetadataNode commentsNode = getNode(root, "CommentExtensions");
        commentsNode.setAttribute("CommentExtension", "Created by: Xceptance");

        IIOMetadataNode appExtensionsNode = getNode(root, "ApplicationExtensions");
        IIOMetadataNode child = new IIOMetadataNode("ApplicationExtension");
        child.setAttribute("applicationID", "NETSCAPE");
        child.setAttribute("authenticationCode", "2.0");

        int loopContinuously = loop ? 1 : 0;
        child.setUserObject(new byte[]
            {
             (byte) loopContinuously , 0x0, 0x0
            });
        appExtensionsNode.appendChild(child);
        metadata.setFromTree(metaFormatName, root);
    }

    private static IIOMetadataNode getNode(IIOMetadataNode rootNode, String nodeName)
    {
        int nNodes = rootNode.getLength();
        for (int i = 0; i < nNodes; i++)
        {
            if (rootNode.item(i).getNodeName().equalsIgnoreCase(nodeName))
            {
                return (IIOMetadataNode) rootNode.item(i);
            }
        }
        IIOMetadataNode node = new IIOMetadataNode(nodeName);
        rootNode.appendChild(node);
        return (node);
    }

    public void writeToSequence(BufferedImage img) throws IOException
    {
        writer.writeToSequence(new IIOImage(compressImage(resizeImage(img, (int) Math.round(img.getWidth() * imageScaleFactor),
                                                                      (int) Math.round(img.getHeight()
                                                                                       * imageScaleFactor))), null, metadata),
                               params);
    }

    private BufferedImage compressImage(BufferedImage image)
    {
        ByteArrayOutputStream compressed = new ByteArrayOutputStream();

        try (ImageOutputStream outputStream = ImageIO.createImageOutputStream(compressed))
        {

            // NOTE: The rest of the code is just a cleaned up version of your code

            // Obtain writer for JPEG format
            ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName("png").next();
            ImageIO.setUseCache(false);
            // Configure JPEG compression: 70% quality
            ImageWriteParam jpgWriteParam = jpgWriter.getDefaultWriteParam();
            jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            jpgWriteParam.setCompressionQuality((float) imageQuality);

            // Set your in-memory stream as the output
            jpgWriter.setOutput(outputStream);

            // Write image as JPEG w/configured settings to the in-memory stream
            // (the IIOImage is just an aggregator object, allowing you to associate
            // thumbnails and metadata to the image, it "does" nothing)
            jpgWriter.write(null, new IIOImage(image, null, null), jpgWriteParam);

            // Dispose the writer to free resources
            jpgWriter.dispose();
            return ImageIO.read(new ByteArrayInputStream(compressed.toByteArray()));
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return image;
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException
    {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }

    @Override
    public void close() throws IOException
    {
        writer.endWriteSequence();
    }

}