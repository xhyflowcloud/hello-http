package net.lovenn.http;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;

public class HttpUtils {
    static final String CR = "\r";
    static final String LF = "\n";
    static final String CRLF = "\r\n";
    static final String SP = "[\\s]+";
    static final String LCS = "UTF-8";
    static final String COLON = ":";

    static HttpRequest parseHttpRequest(ByteBuffer buffer) {
        HttpRequest request = new HttpRequest();
        //解析HTTP Header
        if(buffer == null) {
            return null;
        }
        buffer.flip();
        byte[] rbs = new byte[buffer.limit()];
        buffer.get(rbs);
        int ibody = ibody(rbs);
        try {
            String rlstr= Charset.forName(LCS).newDecoder().decode(ByteBuffer.wrap(rbs, 0, ibody)).toString();
            String[] rls = rlstr.split(CRLF);
            String[] rlps = rls[0].split(SP);
            request.setMethod(rlps[0].trim());
            request.setRequestURI(rlps[1].trim());
            request.setHttpVersion(rlps[2].trim());
            for (int i = 1; i < rls.length; i++) {
                if(StringUtils.isBlank(rls[i])) {
                    break;
                }
                String[] field = rls[i].split(COLON);
                if(HeadEnum.contain(field[0].trim())) {
                    HeadEnum.IEnum iEnum = HeadEnum.get(field[0].trim());
                    if(iEnum instanceof HeadEnum.GeneralHeadEnum) {
                        request.addGeneralHeader(field[0].trim(), field[1].trim());
                    }

                    if(iEnum instanceof HeadEnum.EntityHeadEnum) {
                        request.addGeneralHeader(field[0].trim(), field[1].trim());
                    }

                    if(iEnum instanceof HeadEnum.RequestHeadEnum) {
                        request.addGeneralHeader(field[0].trim(), field[1].trim());
                    }
                }
            }

        } catch (CharacterCodingException e) {
            e.printStackTrace();
        }
        if(ibody < rbs.length) {
            request.setMessageBody(ByteBuffer.wrap(rbs, ibody, rbs.length - ibody).array());
        }
        return request;
    }

    private static int ibody(byte[] rbs) {
        int ibody = rbs.length;
        for (int i = 4; i < rbs.length; i++) {
            if((rbs[i-4] == 13) && (rbs[i-3] == 10) && (rbs[i-2] == 13) && (rbs[i-1] == 10)){
                ibody =  i;
                break;
            }
        }
        return ibody;
    }
}
