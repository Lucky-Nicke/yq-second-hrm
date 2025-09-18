package service;

import entity.Job;

public interface CompanyService {
    void getJobs();
    boolean addJob(Job job);
    boolean deleteJob(int id);
    void getDepts();
    boolean addDept(Job job);
    boolean deleteDept(int id);
}
