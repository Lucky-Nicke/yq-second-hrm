package service.impl;

import dao.CompanyDao;
import dao.impl.CompanyDaoImpl;
import entity.Job;
import service.CompanyService;

import java.util.List;

public class CompanyServiceImpl implements CompanyService {
    CompanyDao companyDao = new CompanyDaoImpl();
    public static List<Job> jobList;

    @Override
    public void getJobs() {
        jobList = companyDao.getJobs();
    }

    @Override
    public boolean addJob(Job job) {
        return false;
    }

    @Override
    public boolean deleteJob(int id) {
        return false;
    }

    @Override
    public void getDepts() {
        jobList = companyDao.getDepts();
    }

    @Override
    public boolean addDept(Job job) {
        return false;
    }

    @Override
    public boolean deleteDept(int id) {
        return false;
    }
}
