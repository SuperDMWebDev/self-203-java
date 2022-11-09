package com.kms.seft203.dashboard;

import com.kms.seft203.exception.DataNotFoundException;
import com.kms.seft203.user.User;
import com.kms.seft203.widget.Widget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private DashboardRepository dashboardRepository;

    public Dashboard update(Long id, SaveDashboardRequest request, User user) throws DataNotFoundException {
            // check if id exist in repository
            if(!dashboardRepository.existsById(id))
            {
                throw new DataNotFoundException("Dashboard didn't exist for id =" + id);
            }
            //dashboard with id existed

            //create new dashboard
            Dashboard dashboard = Dashboard.create(request,user);
            dashboard.setId(id);

            if(request.getWidgets()!=null && !request.getWidgets().isEmpty())
            {
                for(Widget widget : request.getWidgets())
                {
                    widget.setDashboard(dashboard);
                }
            }
            return dashboardRepository.save(dashboard);



    }

    public List<Dashboard> getAllByUserId(Long userId)
    {
        return dashboardRepository.findAllByUser_Id(userId);
    }
    public Dashboard createDashboard(SaveDashboardRequest saveDashboardRequest, User user)
    {
        Dashboard newDashboard = Dashboard.create(saveDashboardRequest, user);
        dashboardRepository.save(newDashboard);
        return newDashboard;
    }

}
