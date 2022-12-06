package com.fu.fuatsbe;

import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.RequiredArgsConstructor;

@SpringBootTest
@RequiredArgsConstructor
class FuAtsBeApplicationTests {

    @Test
    void Test() throws JSONException {
        String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6Ijk1MWMwOGM1MTZhZTM1MmI4OWU0ZDJlMGUxNDA5NmY3MzQ5NDJhODciLCJ0eXAiOiJKV1QifQ.eyJuYW1lIjoiSHV5IGt1biIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BTG01d3UyT1ExcFJQZHBUeEpYM3gzZ2MxN1R2WW5FaFcwTzFxd3dZb2pBXzZBPXM5Ni1jIiwiaXNzIjoiaHR0cHM6Ly9zZWN1cmV0b2tlbi5nb29nbGUuY29tL3NpZ24taW4tY2UwOTMiLCJhdWQiOiJzaWduLWluLWNlMDkzIiwiYXV0aF90aW1lIjoxNjcwMzIzMjAwLCJ1c2VyX2lkIjoiano1bklWMWUyQVBDOXdiN3hvRkIyRFRkTTBFMyIsInN1YiI6Imp6NW5JVjFlMkFQQzl3Yjd4b0ZCMkRUZE0wRTMiLCJpYXQiOjE2NzAzMjMyMDAsImV4cCI6MTY3MDMyNjgwMCwiZW1haWwiOiJkaW5ocXVhbmdodXlkdEBnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJnb29nbGUuY29tIjpbIjExMTEwOTA3NzY0ODk3MDkxNTE5NyJdLCJlbWFpbCI6WyJkaW5ocXVhbmdodXlkdEBnbWFpbC5jb20iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJnb29nbGUuY29tIn19.t7JUDdlVHVjfB7Pi1ZpnuB4cn1khDxlMRhtAREDwi0BCggPnVrScxM2VXAUUNBkchE2hSYQbdmnZzZ_a7GRALAqfTDVcOLVWZRHh7FpgsxU_Txi1NqZJXofyxNkkwxMlxjh36BXeGt-qPiFGXPfecyVuWmGWCcZvdNHs-076-fWZBt6xggf_SHH9zffunZ7IKorcZBddDfOjNYNipW8VPUjtqRB6P7uNUJ-y8Asnm5FYWNSfvX-bezTkLc373qylMcm4xZQiYgO8Sn1opDJ3b27XBj61HssUPdyg6b2TYb68jJEcXWQ318L_iy4o7Putw6HEAwLIbScb7hefLgSYRw";
        String[] split_string = token.split("\\.");
        String base64EncodedBody = split_string[1];

        Base64 base64Url = new Base64(true);
        String body = new String(base64Url.decode(base64EncodedBody));
        JSONObject jsonObject = new JSONObject(body);
        String email = jsonObject.get("email").toString();

        System.out.println(email);
    }


}
