package jp.co.sample.emp_management.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.emp_management.domain.Employee;
import jp.co.sample.emp_management.form.EmployeeSearchForm;
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
			System.out.println("koko");
			model.addAttribute("error","＊検索結果はありませんでした");
			return showList((Integer)session.getAttribute("page"),model);
		}
		model.addAttribute("employeeList",employeeList);
		model.addAttribute("searchName",searchName);
		session.setAttribute("page",page);
		model.addAttribute("size", employeeList.size());
		return "employee/search";
	}
}
