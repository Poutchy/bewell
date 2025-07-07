import React, { useContext } from "react";
import { Link } from "react-router-dom";
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import { ContextUser } from "../contexts/contextUser";

export function Header () {
    const { userToken, userId } = useContext(ContextUser)

    return(
    <div className="container" style={{
        display: "flex",
        flexDirection: "row",
        justifyContent: "space-around",
        alignItems: "center",
        margin: "1%",
        background: "beige"
    }}>
        <Link to="/">
            <h1>BEWELL</h1>
        </Link>
        <Link to="/authentication" style={{
            display: "flex",
            flexDirection: "row",
            justifyContent: "flex-end"
        }}>
            <AccountCircleIcon variant="contained" sx={{ fontSize: "4rem" }} />
        </Link>
    </div>
    )
}

//  variant="contained" sx={{ fontSize:"4rem" }}