package stan.cudgel.di;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface Connection
{
    Answer get(String url, Map<String, String> params)
            throws IOException;
    Answer post(String url, Map<String, String> params, String body)
            throws IOException;
    Answer post(String url, Map<String, String> params, List<Part> body, List<FilePart> files)
            throws IOException;

    class Answer
    {
        private String data;
        private int code;
        public Answer(String d, int c)
        {
            data = d;
            code = c;
        }

        public String getData()
        {
            return data;
        }
        public int getCode()
        {
            return code;
        }
    }

    class Part
    {
        public String key;
        public String value;
    }
    class FilePart
    {
        public String type;
        public String name;
        public byte[] data;
    }
}