package dao.impl;

import dao.CompanyDao;
import entity.Job;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import utils.DruidUtils;

import java.sql.Connection;
import java.util.List;

import static utils.DruidUtils.qr;

public class CompanyDaoImpl implements CompanyDao {
    Connection conn;

    @Override
    public List<Job> getJobs() {
        try {
            conn = DruidUtils.getConnection();
            String sql = "select * from job_inf";
            return qr.query(conn, sql,new BeanListHandler<>(Job.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DruidUtils.closeConnection(conn);
        }
    }

    @Override
    public int addJob(Job job) {
        try {
            conn = DruidUtils.getConnection();
            String sql = "insert into job_inf (ID,NAME,REMARK) values (?,?,?)";
            return qr.update(conn, sql,job.getId(),job.getNAME(),job.getREMARK());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DruidUtils.closeConnection(conn);
        }
    }

    @Override
    public int deleteJob(int id) {
        try {
            conn = DruidUtils.getConnection();
            String sql = "delete from job_inf where id=?";
            return qr.update(conn, sql,id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DruidUtils.closeConnection(conn);
        }
    }

    @Override
    public List<Job> getDepts() {
        try {
            conn = DruidUtils.getConnection();
            String sql = "select * from dept_inf";
            return qr.query(conn, sql,new BeanListHandler<>(Job.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DruidUtils.closeConnection(conn);
        }
    }

    @Override
    public int addDept(Job job) {
        try {
            conn = DruidUtils.getConnection();
            String sql = "insert into dept_inf (ID,NAME,REMARK) values (?,?,?)";
            return qr.update(conn, sql,job.getId(),job.getNAME(),job.getREMARK());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DruidUtils.closeConnection(conn);
        }
    }

    @Override
    public int deleteDept(int id) {
        try {
            conn = DruidUtils.getConnection();
            String sql = "delete from dept_inf where id=?";
            return qr.update(conn, sql,id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DruidUtils.closeConnection(conn);
        }
    }
}
