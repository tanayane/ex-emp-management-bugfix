package jp.co.sample.emp_management.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import jp.co.sample.emp_management.domain.Employee;
import jp.co.sample.emp_management.form.EmployeeSearchForm;
import jp.co.sample.emp_management.form.InsertEmployeeForm;
import jp.co.sample.emp_management.form.UpdateEmployeeForm;
import jp.co.sample.emp_management.service.EmployeeService;

/**
 * 従業員情報を操作するコントローラー.
 * 
 * @author igamasayuki
 *
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private HttpSession session;

	
	/**
	 * 使用するフォームオブジェクトをリクエストスコープに格納する.
	 * 
	 * @return フォーム
	 */
	@ModelAttribute
	public UpdateEmployeeForm setUpForm() {
		return new UpdateEmployeeForm();
	}
	@ModelAttribute
	public EmployeeSearchForm setUpSearchForm() {
		return new EmployeeSearchForm();
	}
	@ModelAttribute
	public InsertEmployeeForm setUpInsertForm() {
		return new InsertEmployeeForm();
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員一覧を表示する
	/////////////////////////////////////////////////////
	/**
	 * 従業員一覧画面を出力します.
	 * 
	 * @param model モデル
	 * @return 従業員一覧画面
	 */
	@RequestMapping("/showList")
	public String showList(Integer page,Model model) {
		String name = (String) session.getAttribute("administratorName");
		if (name == null) {
			return "redirect:/";
		}
		List<Employee> employeeList = employeeService.showList(page);
		model.addAttribute("employeeList", employeeList);
		session.setAttribute("page",page);
		model.addAttribute("size", employeeList.size());
		model.addAttribute("names",employeeService.findAllNames());
		return "employee/list";
	}

	
	/////////////////////////////////////////////////////
	// ユースケース：従業員詳細を表示する
	/////////////////////////////////////////////////////
	/**
	 * 従業員詳細画面を出力します.
	 * 
	 * @param id リクエストパラメータで送られてくる従業員ID
	 * @param model モデル
	 * @return 従業員詳細画面
	 */
	@RequestMapping("/showDetail")
	public String showDetail(String id, Model model) {
		String name = (String) session.getAttribute("administratorName");
		if (name == null) {
			return "redirect:/";
		}

		Employee employee = employeeService.showDetail(Integer.parseInt(id));
		model.addAttribute("employee", employee);
		return "employee/detail";
	}
	
	/////////////////////////////////////////////////////
	// ユースケース：従業員詳細を更新する
	/////////////////////////////////////////////////////
	/**
	 * 従業員詳細(ここでは扶養人数のみ)を更新します.
	 * 
	 * @param form
	 *            従業員情報用フォーム
	 * @return 従業員一覧画面へリダクレクト
	 */
	@RequestMapping("/update")
	public String update(@Validated UpdateEmployeeForm form, BindingResult result, Model model) {
		String name = (String) session.getAttribute("administratorName");
		if (name == null) {
			return "redirect:/";
		}

		if(result.hasErrors()) {
			return showDetail(form.getId(), model);
		}
		Employee employee = new Employee();
		employee.setId(form.getIntId());
		employee.setDependentsCount(form.getIntDependentsCount());
		employeeService.update(employee);
		
		Integer page=(Integer)session.getAttribute("page");
		if(page==null) {page=1;}
		return "redirect:/employee/showList?page="+page;
	}
	
	
	/////////////////////////////////////////////////////
	// ユースケース：従業員を名前で曖昧検索する
	/////////////////////////////////////////////////////

	@RequestMapping("/search")
	public String search(String searchName,Integer page,Model model) {
		String name = (String) session.getAttribute("administratorName");
		if (name == null) {
			return "redirect:/";
		}
		List<Employee> employeeList=employeeService.findByName(searchName,page);
		if(employeeList==null) {
			model.addAttribute("error","＊検索結果はありませんでした");
			return showList((Integer)session.getAttribute("page"),model);
		}
		model.addAttribute("employeeList",employeeList);
		model.addAttribute("searchName",searchName);
		session.setAttribute("page",page);
		model.addAttribute("size", employeeList.size());
		model.addAttribute("names",employeeService.findAllNames());
		return "employee/search";
	}
	
	/////////////////////////////////////////////////////
	// ユースケース：従業員を登録する
	/////////////////////////////////////////////////////
	/**
	 * 従業員登録ページに遷移.
	 * 
	 * @return 従業員登録ページ
	 */
	@RequestMapping("/toInsert")
	public String toInsert() {
		return "employee/insert";
	}
	
	/**
	 * 従業員情報登録.
	 * 
	 * @param form 入力された新規従業員情報
	 * @param result エラーチェック
	 * @return 成功時：従業員リストページ　失敗時：従業員登録ページ
	 */
	@RequestMapping("/insert")
	public String insert(@Validated InsertEmployeeForm form,BindingResult result) {
		if(result.hasErrors()) {
			System.out.println(result);
			return toInsert();
		}
		Employee employee = new Employee();
		// フォームからドメインにプロパティ値をコピー
		BeanUtils.copyProperties(form, employee);
		MultipartFile inputFile=form.getImage();
		employee.setImage(inputFile.getOriginalFilename());
	    try {
	        BufferedInputStream in =
	          new BufferedInputStream(inputFile.getInputStream());
	        File file=new File("C:/env/spring-workspace/ex-emp-management-bugfix/src/main/resources/static/img/" + 
            		inputFile.getOriginalFilename());
	        try {
	            if (file.createNewFile()) {
	                System.out.println("ファイルの作成に成功しました。");
	            } else {
	                System.out.println("ファイルの作成に失敗しました。");
	            }
	        } catch (IOException e) {
	            System.out.println("例外が発生しました。");
	            System.out.println(e);
	        }
	        BufferedOutputStream out =
	          new BufferedOutputStream(
	            new FileOutputStream(file.getAbsolutePath()));
	        FileCopyUtils.copy(in, out);
	        System.out.println(inputFile.getOriginalFilename());
	      } catch (IOException e) {
	    	  result.rejectValue("image", null,"ファイルが正しくアップロードできませんでした");
	    	  System.out.println("kotti");
	      }
		
		employee.setHireDate(Date.valueOf(form.getHireDate()));
		employee.setSalary(Integer.parseInt(form.getSalary()));
		employee.setDependentsCount(Integer.parseInt(form.getDependentsCount()));
		
		employeeService.insert(employee);
		return "redirect:/employee/showList?page=1";			
	}

}
