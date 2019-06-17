package jp.co.sample.emp_management.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.web.multipart.MultipartFile;

/**
 * 新規従業員を登録するフォーム.
 * 
 * @author ayane.tanaka
 *
 */
public class InsertEmployeeForm {

	/**	  名前 */
	@NotBlank(message="名前を入力してください")
	private String name;
	/**	  写真 */
	//@NotBlank(message="写真を選択してください")
	private MultipartFile image;
	/**	  性別 */
	@NotBlank(message="性別を入力してください")
	private String gender;
	/**	  入社日 */
	@NotBlank(message="入社日を入力してください")
	private String hireDate;
	/**	  メールアドレス */
	@Email(message="メールアドレスの形式で入力してください")
	@NotBlank(message="住所を入力してください")
	private String mailAddress;
	/**	  郵便番号 */
	@NotBlank(message="郵便番号を入力してください")
	private String zipCode;
	/**	  住所 */
	@NotBlank(message="住所を入力してください")
	private String address;
	/**	  電話 */
	@NotBlank(message="電話番号を入力してください")
	private String telephone;
	/**	  給料 */
	@Pattern(regexp="^[0-9]+$", message="給料は数値で入力してください")
	private String salary;
	/**	  性格 */
	@NotBlank(message="特性を入力してください")
	private String characteristics;
	/**	 扶養人数 */
	@Pattern(regexp="^[0-9]+$", message="扶養人数は数値で入力してください")
	private String dependentsCount;
	
	
	
	@Override
	public String toString() {
		return "InsertEmployeeForm [name=" + name + ", image=" + image + ", gender=" + gender + ", hire_date="
				+ hireDate + ", mail_address=" + mailAddress + ", zip_code=" + zipCode + ", address=" + address
				+ ", telephone=" + telephone + ", salary=" + salary + ", characteristics=" + characteristics
				+ ", dependents_count=" + dependentsCount + "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public MultipartFile getImage() {
		return image;
	}
	public void setImage(MultipartFile image) {
		this.image = image;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getHireDate() {
		return hireDate;
	}
	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
	}
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public String getCharacteristics() {
		return characteristics;
	}
	public void setCharacteristics(String characteristics) {
		this.characteristics = characteristics;
	}
	public String getDependentsCount() {
		return dependentsCount;
	}
	public void setDependentsCount(String dependentsCount) {
		this.dependentsCount = dependentsCount;
	}
}
