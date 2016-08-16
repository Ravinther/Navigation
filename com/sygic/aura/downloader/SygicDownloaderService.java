package com.sygic.aura.downloader;

import com.google.android.vending.expansion.downloader.impl.DownloaderService;
import com.sygic.aura.ProjectsConsts;

public class SygicDownloaderService extends DownloaderService {
    private final byte[] SALT;

    public SygicDownloaderService() {
        this.SALT = new byte[]{(byte) 32, (byte) 93, (byte) -56, (byte) -38, (byte) 94, (byte) 12, (byte) 22, (byte) -72, (byte) -65, (byte) 42, (byte) 92, (byte) -33, (byte) 1, (byte) -22, (byte) 71, (byte) -84, (byte) 19, (byte) 38, (byte) 82, (byte) -19};
    }

    public String getPublicKey() {
        String PUBLIC_KEY = ProjectsConsts.getString(12);
        if (PUBLIC_KEY == null || PUBLIC_KEY.equals("")) {
            return "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgJvPKxtn4Sr0mSCg5w7sCRFKma3Sksv/WDbGT9lDVhYcCXOOvsbw91oSQ2LlEQcNRNQUL9qR2Ekn2AIbyKN6qKrGs7W1g0JIQsrQMGNK9GBPX29PYTAz5zSJy8JBRSLF4VKDb+S3mjazUaxC3AjJm9+JlDynG/y5BEBBQifM5u7G7ADP3IXzEimXaTaPpdBmZ/72dBZ0ksT+fcPjPbIA2weyKIvMgMSk9jyexHccaCDQMnSqE36ngdOuDn7fPOmY0J63Mi9OtN2yVIGYBsEDPKAeALZaE/E4N9U3OedNmGZProf9j+p33rX64HURavmA6N8HZtLVfcTKqhltxgwCTwIDAQAB";
        }
        return PUBLIC_KEY;
    }

    public byte[] getSALT() {
        return this.SALT;
    }

    public String getAlarmReceiverClassName() {
        return SygicAlarmReceiver.class.getName();
    }
}
