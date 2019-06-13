package jp.co.sample.emp_management.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 404エラー時などにページ遷移を行う.
 * 
 * @author ayane.tanaka
 *
 */
@Controller
public class NotFoundController implements ErrorController{

	private static final String PATH="/error";
	
	/* (non-Javadoc)
	 * @see org.springframework.boot.web.servlet.error.ErrorController#getErrorPath()
	 */
	@Override
	@RequestMapping(PATH)
	public String getErrorPath() {
		//System.out.println("404 error");
		return "common/notfound";
	}
}
