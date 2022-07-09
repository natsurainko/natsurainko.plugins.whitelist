package natsurainko.plugins.whitelist.utils;

import com.google.gson.Gson;
import natsurainko.plugins.whitelist.model.ProfileResponse;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.MessageFormat;

public class UuidHelper {
    public static String formatString(String uuid) {
        return MessageFormat.format("{0}-{1}-{2}-{3}-{4}",
                uuid.substring(0,8),
                uuid.substring(8,12),
                uuid.substring(12,16),
                uuid.substring(16,20),
                uuid.substring(20));
    }

    public static String getNameFromUuid(String uuid) throws Exception{
        HttpRequest request = HttpRequest.newBuilder
                (new URI("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid))
                .GET().build();

        HttpResponse<String> response = StaticResources.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        ProfileResponse profileResponse = new Gson().fromJson(response.body(), ProfileResponse.class);

        return profileResponse.getName();
    }

    public static String getUuidFromName(String name) throws Exception{
        HttpRequest request = HttpRequest.newBuilder
                (new URI("https://api.mojang.com/users/profiles/minecraft/" + name))
                .GET().build();

        HttpResponse<String> response = StaticResources.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        ProfileResponse profileResponse = new Gson().fromJson(response.body(), ProfileResponse.class);

        return profileResponse.getId();
    }
}
