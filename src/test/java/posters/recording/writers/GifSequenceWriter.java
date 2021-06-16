package posters.recording.writers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

import posters.recording.config.RecordingConfigurations;

public class GifSequenceWriter implements Writer
{
    protected ImageWriter writer;

    protected ImageWriteParam params;

    protected IIOMetadata metadata;

    private String videoFileName;

    private RecordingConfigurations recordingConfigurations;

    protected GifSequenceWriter(RecordingConfigurations recordingConfigurations, String videoFileName) throws IOException
    {
        this.recordingConfigurations = recordingConfigurations;
        this.videoFileName = videoFileName;
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
              (byte) loopContinuously, 0x0, 0x0
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

    @Override
    public void start() throws IOException
    {
        ImageOutputStream out = new FileImageOutputStream(new File(videoFileName));
        writer = ImageIO.getImageWritersBySuffix("gif").next();
        params = writer.getDefaultWriteParam();

        ImageTypeSpecifier imageTypeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_4BYTE_ABGR);
        metadata = writer.getDefaultImageMetadata(imageTypeSpecifier, params);

        configureRootMetadata(recordingConfigurations.oneImagePerMilliseconds(), recordingConfigurations.loop());
        writer.setOutput(out);
        writer.prepareWriteSequence(null);
    }

    @Override
    public void write(File image)
    {
        try
        {
            BufferedImage img = ImageIO.read(image);
            writer.writeToSequence(new IIOImage(img, null, metadata), params);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void stop()
    {
        try
        {
            writer.endWriteSequence();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
