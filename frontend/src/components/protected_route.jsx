import React from "react";
import {Outlet, Navigate, useLocation} from "react-router-dom";

export function ProtectedRoutes ({ isAllowed, to }) {
    const location = useLocation();
    if (!isAllowed) {
        const { salon, service } = location.state || {};
        if (salon && service) {
            console.log(salon)
            console.log(service)
        }
    }
    return isAllowed ?  <Outlet /> : <Navigate to={ to }
                                               replace
                                               state={{ from: location.pathname }}
    /> ;
}