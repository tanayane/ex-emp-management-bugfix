package jp.co.sample.emp_management.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 管理者情報登録時に使用するフォーム.
 * 
 * @author igamasayuki
 * 
 */
public class InsertAdministratorForm {
	/** 名前 */
	@NotBlank(message="名前を入力してください")
	private String name;
	/** メールアドレス */
	@NotBlank(message="メールアドレスを入力してください")
	@Email(message="メールアドレス形式で入力してください")
	private String mailAddress;
	/** パスワード */
	@NotBlank(message="パスワードを入力してください")
	private String password;
	/** 確認用パスワード */
	@NotBlank(message="確認用パスワードを入力してください")
	private String comfirmPassword;
	
	
	public String getComfirmPassword() {
		return comfirmPassword;
	}
	public void setComfirmPassword(String comfirmPassword) {
		this.comfirmPassword = comfirmPassword;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "InsertAdministratorForm [name=" + name + ", mailAddress=" + mailAddress + ", password=" + password
				+ ",confirmpassword="+comfirmPassword+"]";
	}
	
}
