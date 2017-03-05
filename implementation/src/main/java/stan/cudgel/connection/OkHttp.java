package stan.cudgel.connection;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import stan.cudgel.di.Connection;

public class OkHttp
    implements Connection
{
    static private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    static private final MediaType FORM_DATA = MultipartBody.FORM;
    static private final MediaType PNG = MediaType.parse("image/png");

    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();

    public Answer get(String url, Map<String, String> params)
            throws IOException
    {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        for(String key : params.keySet())
        {
            urlBuilder.addQueryParameter(key, params.get(key));
        }
        Response response = client.newCall(new Request.Builder().url(urlBuilder.build()).build()).execute();
        return new Answer(response.body().string(), response.code());
    }

    public Answer post(String url, Map<String, String> params, String body)
            throws IOException
    {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        for(String key : params.keySet())
        {
            urlBuilder.addQueryParameter(key, params.get(key));
        }
        Request.Builder builder = new Request.Builder().url(urlBuilder.build()).post(RequestBody.create(JSON, body));
        Response response = client.newCall(builder.build()).execute();
        return new Answer(response.body().string(), response.code());
    }

    public Answer post(String url, Map<String, String> params, List<Part> body, List<FilePart> files)
            throws IOException
    {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        for(String key : params.keySet())
        {
            urlBuilder.addQueryParameter(key, params.get(key));
        }
        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for(Part part : body)
        {
            multipartBodyBuilder.addFormDataPart(part.key, part.value);
        }
        for(FilePart part : files)
        {
            multipartBodyBuilder.addFormDataPart(part.type, part.name, RequestBody.create(PNG, part.data));
        }
        Request.Builder builder = new Request.Builder().url(urlBuilder.build()).post(multipartBodyBuilder.build());
        Response response = client.newCall(builder.build()).execute();
        return new Answer(response.body().string(), response.code());
    }
}