package dao.impl;

import dao.EmployeeDao;
import entity.Employee;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import utils.DruidUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static utils.DruidUtils.getConnection;
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
            String sql = "SELECT " +
                    "emp.ID ,dept.`NAME` as deptName, job.`NAME` as jobNam,emp.`NAME` as name, CARD_ID,ADDRESS, POST_CODE ,TEL ,PHONE ,QQ_NUM ,EMAIL, SEX, PARTY ,BIRTHDAY, RACE, EDUCATION ,SPECIALITY ,HOBBY ,emp.REMARK ,CREATE_DATE " +
                    "FROM " +
                    "employee_inf emp " +
                    "INNER JOIN " +
                    "job_inf job " +
                    "ON " +
                    "emp.JOB_ID = job.ID " +
                    "INNER JOIN " +
                    "dept_inf dept " +
                    "ON " +
                    "dept.ID = emp.DEPT_ID " +
                    "limit ?,?";
            return qr.query(conn, sql, new BeanListHandler<>(Employee.class), startIndex, pageSize);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DruidUtils.closeConnection(conn);
        }
    }

    @Override
    public int getAllEmployeeCount() {
        try {
            conn = DruidUtils.getConnection();
            String sql = "select count(*) from employee_inf";
            Long count = qr.query(conn, sql, new ScalarHandler<>());
            return count.intValue();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DruidUtils.closeConnection(conn);
        }
    }

    @Override
    public int addEmployee(Employee employee) {
        try {
            conn = DruidUtils.getConnection();
            String sql = "insert into employee_inf (dept_id, job_id, name, card_id, address, post_code, tel, phone, qq_num, email, " +
                    "sex, party, birthday, race, education, speciality, hobby, remark) " +
                    "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            return qr.update(conn, sql,
                    employee.getDeptName(),
                    employee.getJobNam(),
                    employee.getName(),
                    employee.getCARD_ID(),
                    employee.getADDRESS(),
                    employee.getPOST_CODE(),
                    employee.getTEL(),
                    employee.getPHONE(),
                    employee.getQQ_NUM(),
                    employee.getEMAIL(),
                    employee.getSEX(),
                    employee.getPARTY(),
                    employee.getBIRTHDAY(),
                    employee.getRACE(),
                    employee.getEDUCATION(),
                    employee.getSPECIALITY(),
                    employee.getHOBBY(),
                    employee.getREMARK());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DruidUtils.closeConnection(conn);
        }
    }

    @Override
    public int deleteEmployee(int delId) {
        try {
            conn = DruidUtils.getConnection();
            String sql = "delete from employee_inf where id=?";
            return qr.update(conn, sql, delId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DruidUtils.closeConnection(conn);
        }
    }

    @Override
    public int updateEmployee(Employee employee) {
        try {
            conn = DruidUtils.getConnection();
            String sql = "update employee_inf set DEPT_ID=?, JOB_ID=?, NAME=?, CARD_ID=?,ADDRESS=?, POST_CODE=?, TEL=?, PHONE=?, QQ_NUM=?, EMAIL=?,SEX=?, PARTY=?, BIRTHDAY=?, RACE=?, EDUCATION=?, SPECIALITY=?, HOBBY=?, REMARK=? WHERE ID=?";
            return qr.update(conn, sql,
                    employee.getDeptName(),
                    employee.getJobNam(),
                    employee.getName(),
                    employee.getCARD_ID(),
                    employee.getADDRESS(),
                    employee.getPOST_CODE(),
                    employee.getTEL(),
                    employee.getPHONE(),
                    employee.getQQ_NUM(),
                    employee.getEMAIL(),
                    employee.getSEX(),
                    employee.getPARTY(),
                    employee.getBIRTHDAY(),
                    employee.getRACE(),
                    employee.getEDUCATION(),
                    employee.getSPECIALITY(),
                    employee.getHOBBY(),
                    employee.getREMARK(),
                    employee.getID());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DruidUtils.closeConnection(conn);
        }
    }

    @Override
    public List<Employee> searchEmployeeInfoByName(String username, String page, String limit) {
        String searchUsername = "%" + username + "%";
        try {
            conn = getConnection();
            int pageNum = page == null || page.isEmpty() ? 1 : Integer.parseInt(page);
            int pageSize = limit == null || limit.isEmpty() ? 10 : Integer.parseInt(limit);

            int startIndex = (pageNum - 1) * pageSize;
            String sql = "SELECT " +
                    "emp.ID ,dept.`NAME` as deptName, job.`NAME` as jobNam,emp.`NAME` as name, CARD_ID,ADDRESS, POST_CODE ,TEL ,PHONE ,QQ_NUM ,EMAIL, SEX, PARTY ,BIRTHDAY, RACE, EDUCATION ,SPECIALITY ,HOBBY ,emp.REMARK ,CREATE_DATE " +
                    "FROM " +
                    "employee_inf emp " +
                    "INNER JOIN " +
                    "job_inf job " +
                    "ON " +
                    "emp.JOB_ID = job.ID " +
                    "INNER JOIN " +
                    "dept_inf dept " +
                    "ON " +
                    "dept.ID = emp.DEPT_ID " +
                    "where emp.`NAME` like ? limit ?,?";
            return qr.query(conn, sql, new BeanListHandler<>(Employee.class),searchUsername, startIndex, pageSize);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DruidUtils.closeConnection(conn);
        }
    }

    @Override
    public int getSearchEmployeeInfoByNameCount(String username) {
        String searchUsername = "%" + username + "%";
        try {
            conn = getConnection();
            String sql = "select count(*) from employee_inf where NAME like ?";
            Long count = qr.query(conn, sql, new ScalarHandler<>(), searchUsername);
            return count.intValue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DruidUtils.closeConnection(conn);
        }
    }
}
