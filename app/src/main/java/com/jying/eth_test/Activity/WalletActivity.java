package com.jying.eth_test.Activity;

import android.Manifest;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jying.eth_test.R;

import org.web3j.crypto.Bip39Wallet;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.MnemonicUtils;
import org.web3j.crypto.WalletUtils;
import org.web3j.utils.Numeric;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.novacrypto.bip39.MnemonicGenerator;
import io.github.novacrypto.bip39.Words;
import io.github.novacrypto.bip39.wordlists.English;
import io.github.novacrypto.hashing.Sha256;

public class WalletActivity extends AppCompatActivity {

    @BindView(R.id.wallet_ed_psd)
    EditText ed_psd;
    @BindView(R.id.wallet_bt_create)
    Button bt_create;
    @BindView(R.id.wallet_detail)
    TextView tv_wallet;
    @BindView(R.id.wallet_pro)
    ProgressBar progressBar;
    public static final String TAG = "=======debug=======";
    @BindView(R.id.wallet_bt_copy)
    Button bt_copy;
    @BindView(R.id.wallet_goto)
    Button bt_connect;
    private static final String has_eth_pri = "0x2baf6571ae20064242d7472de0531f758567ea3f5a165c89a7d6f8d9e48ba901";
    private static final String has_eth_pub = "0xd439e986f8d7ca72cc1bd53bbe8ca9b0401036e9";
    String walletFile = null;
    @BindView(R.id.wallet_cutter)
    TextView tv_cutter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_wallet);
        ButterKnife.bind(this);
        if (tv_wallet.getText().toString().equals("")) {
            bt_copy.setVisibility(View.GONE);
        }

    }

    @OnClick({R.id.wallet_bt_create, R.id.wallet_bt_copy, R.id.wallet_goto, R.id.wallet_go})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.wallet_bt_create:
                String password = ed_psd.getText().toString();
                int permissionCheck = ContextCompat.checkSelfPermission(WalletActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            WalletActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            123);
                } else {
                    createWallet(password);
                }
                break;
            case R.id.wallet_bt_copy:
                if (tv_wallet.getText().toString().equals("")) return;
                ClipboardManager clipboardManager = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setText(tv_wallet.getText().toString());
                break;
            case R.id.wallet_goto:
                SharedPreferences sp1 = getSharedPreferences("address", Context.MODE_PRIVATE);
                String ad = sp1.getString("address", "0");
                String pri = sp1.getString("private", "0");
                if (!ad.equals("0") && !pri.equals("0")) {
                    tv_cutter.setText("当前钱包：" + ad + "\n" + "私钥：" + pri);
                } else {
                    Toast.makeText(this, "钱包文件不存在", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.wallet_go:
                SharedPreferences sp2 = getSharedPreferences("address", Context.MODE_PRIVATE);
                String check_address = sp2.getString("address", "0");
                if (!check_address.equals("0")) {
                    startActivity(new Intent(WalletActivity.this, XingqiuActivity.class));
                } else {
                    Toast.makeText(WalletActivity.this, "钱包文件不存在", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 123:
                if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(WalletActivity.this, "获取权限失败", Toast.LENGTH_SHORT).show();
                } else {
                    createWallet(ed_psd.getText().toString());
                }
                break;
        }
    }

    private void createWallet(String password) {
        progressBar.setVisibility(View.VISIBLE);
        tv_wallet.setText("");
        if (password.isEmpty()) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(WalletActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        File fileDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS); //默认的手机根目录download
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }

        StringBuilder sb = new StringBuilder();
        byte[] entropy = new byte[Words.TWELVE.byteLength()];
        new SecureRandom().nextBytes(entropy);
        new MnemonicGenerator(English.INSTANCE).createMnemonic(entropy, sb::append);
        String mnemonics = sb.toString();
        Log.e(TAG, "生成的助记词：" + mnemonics);

        new Thread(new Runnable() {
            @Override
            public void run() {
                //password为输入的钱包密码
                byte[] seed = MnemonicUtils.generateSeed(mnemonics, password);
                ECKeyPair privateKey = ECKeyPair.create(Sha256.sha256(seed));
                try {
                    walletFile = WalletUtils.generateWalletFile(password, privateKey, fileDir, false);
                } catch (CipherException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }


                Bip39Wallet bip39Wallet = new Bip39Wallet(walletFile, mnemonics);

                Log.e(TAG, bip39Wallet.getFilename()); //生成的json文件名

                //导入助记词获取钱包地址
                Credentials credentials = WalletUtils.loadBip39Credentials(password, bip39Wallet.getMnemonic());
                Log.e(TAG, "导入助记词获取钱包地址:" + credentials.getAddress());

                String msg = "助记词:" + bip39Wallet.getMnemonic()
                        + "\n\n钱包地址:" + credentials.getAddress()
                        + "\n\n私钥:" + Numeric.encodeQuantity(credentials.getEcKeyPair().getPrivateKey())
                        + "\n\n公钥:" + Numeric.encodeQuantity(credentials.getEcKeyPair().getPublicKey())
                        + "\n\nkeystore密码：" + password
                        + "\n\n生成的keystore地址：" + fileDir + "/" + bip39Wallet.getFilename();

                SharedPreferences sp = getSharedPreferences("address", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("address", credentials.getAddress());
                editor.putString("private", Numeric.encodeQuantity(credentials.getEcKeyPair().getPrivateKey()));
                editor.commit();
                Log.e(TAG, msg);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ed_psd.setText("");
                        tv_wallet.setText(msg);
                        progressBar.setVisibility(View.GONE);
                        if (!tv_wallet.getText().toString().equals("")) {
                            bt_copy.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        }).start();
    }
}
