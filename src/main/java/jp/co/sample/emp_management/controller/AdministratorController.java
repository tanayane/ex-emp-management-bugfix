package jp.co.sample.emp_management.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.emp_management.domain.Administrator;
import jp.co.sample.emp_management.form.InsertAdministratorForm;
import jp.co.sample.emp_management.form.LoginForm;
import jp.co.sample.emp_management.service.AdministratorService;

/**
 * 管理者情報を操作するコントローラー.
 * 
 * @author igamasayuki
 *
 */
/**
 * @author ayane.tanaka
 *
 */
@Controller
@RequestMapping("/")
public class AdministratorController {

	@Autowired
	private AdministratorService administratorService;
	
	@Autowired
	private HttpSession session;

	/**
	 * 使用するフォームオブジェクトをリクエストスコープに格納する.
	 * 
	 * @return フォーム
	 */
	@ModelAttribute
	public InsertAdministratorForm setUpInsertAdministratorForm() {
		return new InsertAdministratorForm();
	}
	
	//  (SpringSecurityに任せるためコメントアウトしました)
	@ModelAttribute
	public LoginForm setUpLoginForm() {
		return new LoginForm();
	}

	/////////////////////////////////////////////////////
	// ユースケース：管理者を登録する
	/////////////////////////////////////////////////////
	/**
	 * 管理者登録画面を出力します.
	 * 
	 * @return 管理者登録画面
	 */
	@RequestMapping("/toInsert")
	public String toInsert() {
		return "administrator/insert";
	}

	/**
	 * 管理者情報を登録します.
	 * 
	 * @param form
	 *            管理者情報用フォーム
	 * @return ログイン画面へリダイレクト
	 */
	@RequestMapping("/insert")
	public String insert(@Validated InsertAdministratorForm form,BindingResult result) {
		String mail=form.getMailAddress();
		if(! administratorService.existMail(mail)) {
			result.rejectValue("mailAddress",null, "メールアドレスは既に使われています");
		}
		if(! form.getPassword().equals(form.getComfirmPassword())) {
			result.rejectValue("comfirmPassword", null,"同じパスワードを入力してください");
		}
		if(result.hasErrors()) {
			return toInsert();
		}
		Administrator administrator = new Administrator();
		// フォームからドメインにプロパティ値をコピー
		BeanUtils.copyProperties(form, administrator);
		administratorService.insert(administrator);
		return "redirect:/";
				
	}

	/////////////////////////////////////////////////////
	// ユースケース：ログインをする
	/////////////////////////////////////////////////////
	/**
	 * ログイン画面を出力します.
	 * 
	 * @return ログイン画面
	 */
	@RequestMapping("/")
	public String toLogin() {
		String name=(String)session.getAttribute("administratorName");
		if(name!=null) {
			return "redirect:/employee/showList?page="+getPage();
		}
		return "administrator/login";
	}

	/**
	 * ログインします.
	 * 
	 * @param form
	 *            管理者情報用フォーム
	 * @param result
	 *            エラー情報格納用オブッジェクト
	 * @return ログイン後の従業員一覧画面
	 */
	@RequestMapping("/login")
	public String login(@Validated LoginForm form, BindingResult result, Model model) {

		Administrator administrator = administratorService.login(form.getMailAddress(), form.getPassword());
		if (administrator == null) {
			result.rejectValue("password",null, "メールアドレスまたはパスワードが不正です。");
			return toLogin();
		}
		session.setAttribute("administratorName", administrator.getName());
		session.setAttribute("administratorId", administrator.getId());		
		return "redirect:/employee/showList?page="+getPage();
	}
	
	/////////////////////////////////////////////////////
	// ユースケース：ログアウトをする
	/////////////////////////////////////////////////////
	/**
	 * ログアウトをします. (SpringSecurityに任せるためコメントアウトしました)
	 * 
	 * @return ログイン画面
	 */
	@RequestMapping(value = "/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/";
	}
	
	/**
	 * 従業員リストの現在ページ数を返す.
	 * 
	 * @return 現在のページ数
	 */
	public Integer getPage() {
		Integer page=(Integer)session.getAttribute("page");
		if(page==null) {
			return 1;
		}
		return page;
	}

}
