package com.jying.eth_test.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    public String crypt(String str) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("String to encript cannot be null or zero length");
        }
        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] hash = md.digest();
            for (int i = 0; i < hash.length; i++) {
                if ((0xff & hash[i]) < 0x10) {
                    hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
                } else {
                    hexString.append(Integer.toHexString(0xFF & hash[i]));
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hexString.toString();
    }

    public static void main(String args[]) {
        String params = "0000000000000000000000000000000000000000000000000000000000000020000000000000000000000000000000000000000000000000000000000000000b68656c6c6f2072656d6978000000000000000000000000000000000000000000";
        String byteCode = "0x608060405234801561001057600080fd5b506040516104e93803806104e983398101604052805101805161003a906000906020840190610041565b50506100dc565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061008257805160ff19168380011785556100af565b828001600101855582156100af579182015b828111156100af578251825591602001919060010190610094565b506100bb9291506100bf565b5090565b6100d991905b808211156100bb57600081556001016100c5565b90565b6103fe806100eb6000396000f3006080604052600436106100565763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416631f1bd692811461005b578063428ebb4a146100e5578063954ab4b214610140575b600080fd5b34801561006757600080fd5b50610070610155565b6040805160208082528351818301528351919283929083019185019080838360005b838110156100aa578181015183820152602001610092565b50505050905090810190601f1680156100d75780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b3480156100f157600080fd5b506040805160206004803580820135601f810184900484028501840190955284845261013e9436949293602493928401919081908401838280828437509497506101e39650505050505050565b005b34801561014c57600080fd5b506100706101fa565b6000805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156101db5780601f106101b0576101008083540402835291602001916101db565b820191906000526020600020905b8154815290600101906020018083116101be57829003601f168201915b505050505081565b80516101f690600090602084019061033a565b5050565b60408051602080825260008054600260001961010060018416150201909116049183018290526060937f75521f0a323fc157340783e47a8ce6002cff55746d372f0fd9207413b0f670f593919282918201908490801561029b5780601f106102705761010080835404028352916020019161029b565b820191906000526020600020905b81548152906001019060200180831161027e57829003601f168201915b50509250505060405180910390a16000805460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152929183018282801561032f5780601f106103045761010080835404028352916020019161032f565b820191906000526020600020905b81548152906001019060200180831161031257829003601f168201915b505050505090505b90565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061037b57805160ff19168380011785556103a8565b828001600101855582156103a8579182015b828111156103a857825182559160200191906001019061038d565b506103b49291506103b8565b5090565b61033791905b808211156103b457600081556001016103be5600a165627a7a72305820294f653d9393f3867f5fb4581745a8a3644f2beb9a6da79ccf56b6d16bc49af50029";
        String email = "853281682@qq.com";
        String secret = "ace21ce8bbad4701971235bb422515ae";
        int secret_id = 100146;
        int id = 11519;
        String sign = "email=" + email + "&bytecode=" + byteCode + "&params=" + params + "&secret=" + secret;
        String signbyid = "email=" + email + "&id=" + id + "&secret=" + secret;
        String sign_prepay_id="";
        MD5Util md5Util = new MD5Util();
        String text = md5Util.crypt(signbyid);
        System.out.println(text);
    }
}
