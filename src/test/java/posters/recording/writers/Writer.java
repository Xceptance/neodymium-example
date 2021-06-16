package posters.recording.writers;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import posters.recording.config.RecordingConfigurations;

public interface Writer
{
    public static Writer instanciate(Class<? extends Writer> writer, RecordingConfigurations recordingConfigurations, String fileName)
    {
        Writer writerObject = null;
        try
        {
            Constructor<? extends Writer> constructor = writer.getDeclaredConstructor(RecordingConfigurations.class, String.class);
            writerObject = constructor.newInstance(recordingConfigurations, fileName);
        }
        catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException
            | InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return writerObject;
    }

    public default BufferedImage compressImage(BufferedImage image, double imageQuality)
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

    public default BufferedImage resizeImage(BufferedImage originalImage, double scaleFactor)
    {
        int targetWidth = (int) Math.round(originalImage.getWidth() * scaleFactor);
        int targetHeight = (int) Math.round(originalImage.getHeight() * scaleFactor);
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }

    public void start() throws IOException;

    public void write(File image);

    public void stop();
}
