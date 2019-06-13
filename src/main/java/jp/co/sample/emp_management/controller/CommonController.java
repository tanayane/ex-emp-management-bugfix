package jp.co.sample.emp_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * エラー発生時にエラーページに遷移させる.
 * 
 * @author ayane.tanaka
 *
 */
@Controller
@RequestMapping("/common")
public class CommonController {

	
	/**
	 * エラーページを表示する.
	 * 
	 * @return エラーページ
	 */
	@RequestMapping("/maintenance")
	public String maintenance() {
		return "common/maintenance";
	}
}
