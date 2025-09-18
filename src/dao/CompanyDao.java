package dao;

import entity.Job;

import java.util.List;

public interface CompanyDao {
    List<Job> getJobs();
    int addJob(Job job);
    int deleteJob(int id);
    List<Job> getDepts();
    int addDept(Job job);
    int deleteDept(int id);
}
