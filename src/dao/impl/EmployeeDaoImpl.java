package dao.impl;

import dao.EmployeeDao;
import entity.Employee;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import utils.DruidUtils;

import java.sql.Connection;
import java.util.List;

import static utils.DruidUtils.qr;

public class EmployeeDaoImpl implements EmployeeDao {
    Connection conn;

    @Override
    public List<Employee> getEmployeeList(String page, String limit) {
        try {
            conn = DruidUtils.getConnection();
            int pageNum = page == null || page.isEmpty() ? 1 : Integer.parseInt(page);
            int pageSize = limit == null || limit.isEmpty() ? 10 : Integer.parseInt(limit);

            int startIndex = (pageNum - 1) * pageSize;
            String sql = "select * from employee_inf limit ?,?";
            return qr.query(conn,sql,new BeanListHandler<>(Employee.class),startIndex, pageSize);
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            DruidUtils.closeConnection(conn);
        }
    }

    @Override
    public int getAllEmployeeCount() {
        try{
            conn = DruidUtils.getConnection();
            String sql = "select count(*) from employee_inf";
            Long count = qr.query(conn,sql,new ScalarHandler<>());
            return count.intValue();
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            DruidUtils.closeConnection(conn);
        }
    }
}
