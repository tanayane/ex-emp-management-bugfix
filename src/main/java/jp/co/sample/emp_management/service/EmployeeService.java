package jp.co.sample.emp_management.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.emp_management.domain.Employee;
import jp.co.sample.emp_management.repository.EmployeeRepository;

/**
 * 従業員情報を操作するサービス.
 * 
 * @author igamasayuki
 *
 */
@Service
@Transactional
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	/**
	 * 従業員情報を全件取得します.
	 * 
	 * @return　従業員情報一覧
	 */
	@SuppressWarnings("finally")
	public List<Employee> showList(Integer page) {
		List<Employee> employeeList = employeeRepository.findAll();
		List<Employee> fragmentsEmployeeList = new ArrayList<>();
		try {
			for (int i = (page-1)*10; i < page * 10; i++) {
				fragmentsEmployeeList.add(employeeList.get(i));
			}
		}catch(Exception e) {
			
		}finally{
			return fragmentsEmployeeList;
		}

	}
	
	/**
	 * 従業員情報を取得します.
	 * 
	 * @param id ID
	 * @return 従業員情報
	 * @throws 検索されない場合は例外が発生します
	 */
	public Employee showDetail(Integer id) {
		Employee employee = employeeRepository.load(id);
		return employee;
	}
	
	/**
	 * 従業員情報を更新します.
	 * 
	 * @param employee　更新した従業員情報
	 */
	public void update(Employee employee) {
		employeeRepository.update(employee);
	}
	
	/**
	 * 名前から従業員を曖昧検索する.
	 * 
	 * @param name 検索したい名前
	 * @return 検索結果のリスト
	 */
	@SuppressWarnings("finally")
	public List<Employee> findByName(String name,Integer page){
		List<Employee>employeeList=employeeRepository.findByName(name);
		if(employeeList.size()==0) {return null;}
		List<Employee> fragmentsEmployeeList = new ArrayList<>();
		try {
			for (int i = (page-1)*10; i < page * 10; i++) {
				fragmentsEmployeeList.add(employeeList.get(i));
			}
		}catch(Exception e) {
			
		}finally{
			return fragmentsEmployeeList;
		}

	}
	
	
	/**
	 * 従業員の名前リストを検索.
	 * 
	 * @return 従業員の名前リスト
	 */
	public List<String> findAllNames(){
		return employeeRepository.findAllNames();
	}
	
	/**
	 * 新規従業員の登録.
	 * 
	 * @param employee 登録する従業員情報
	 */
	public void insert(Employee employee) {
		employeeRepository.insert(employee);
	}
}
