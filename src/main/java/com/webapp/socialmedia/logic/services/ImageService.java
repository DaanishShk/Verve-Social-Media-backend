package com.webapp.socialmedia.logic.services;

import com.webapp.socialmedia.domain.model.image.Image;
import com.webapp.socialmedia.domain.repositories.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;
import java.io.FileInputStream;

@Service
public class ImageService {

   @Autowired
    private ImageRepository imageRepository;

   @Autowired
   private AccountService accountService;

   @Autowired
    private AccountProfileService accountProfileService;
   private final Logger logger = LoggerFactory.getLogger(ImageService.class);

    public void saveImage(Image image) {
       imageRepository.save(image);
   }

    public ResponseEntity<byte[]> viewProfilePicture(@PathVariable String username) {
        Image image = accountService.getByUsername(username).getProfilePic();
        // TODO replace hardcoded image address
        if(image == null) {
            File file = new File("D:\\Git Repositories\\Social-Media-webapp\\Backend\\src\\main\\resources\\static\\default_profilePicture.jpg"); //windows
            byte[] bFile = new byte[(int) file.length()];

            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                fileInputStream.read(bFile);
                fileInputStream.close();
            } catch (Exception e) {
                logger.info("No profile picture for user " + username);
            }
            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(bFile, headers, HttpStatus.FOUND);
        }

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(image.getMediaType()));
        return new ResponseEntity<>(image.getContent(), headers, HttpStatus.FOUND);
    }

    public ResponseEntity<byte[]> viewBackground(@PathVariable String username) {
        Image image = accountProfileService.getBackgroundFromUsername(username);

        if(image == null) {
           File file = new File("D:\\Git Repositories\\Social-Media-webapp\\Backend\\src\\main\\resources\\static\\default_background.jpg"); //windows
            byte[] bFile = new byte[(int) file.length()];

            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                fileInputStream.read(bFile);
                fileInputStream.close();
            } catch (Exception e) {
                logger.info("No background for user " + username);
            }
            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(bFile, headers, HttpStatus.FOUND);
        }

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(image.getMediaType()));
        return new ResponseEntity<>(image.getContent(), headers, HttpStatus.FOUND);
    }

//   public Image getImage(String username) {
//
//   }

//    public static Dimensions getImageDimensionFromUrl(final URL url)
//            throws IOException {
//
//        try (ImageInputStream in = ImageIO.createImageInputStream(url.openStream())) {
//            final Iterator<ImageReader> readers = ImageIO.getImageReaders(in);
//            if (readers.hasNext()) {
//                final ImageReader reader = readers.next();
//                try {
//                    reader.setInput(in);
//                    return new Dimensions(reader.getWidth(0), reader.getHeight(0));
//                } finally {
//                    reader.dispose();
//                }
//            }
//        }
//
//        return null;
//    }

}
