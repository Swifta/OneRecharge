package com.swifta.onerecharge.util;

/**
 * Created by moyinoluwa on 9/8/16.
 */
public class ImageNameBuilder {

    public static String buildAmazonDocumentName(String uploadtype, String name, String
            imageExtension) {

        String imageName = String.format("public/uploads/agents/%s-%s%s",
                uploadtype, name, imageExtension);

        return imageName;
    }
}
