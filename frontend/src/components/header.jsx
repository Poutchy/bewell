import React, { useContext } from "react";
import { Link } from "react-router-dom";
import { ContextUser } from "../contexts/contextUser";

export function Header () {
    const { userToken, userId } = useContext(ContextUser)

    return(
        <div className="container" style={{
            display:"flex",
            flexDirection:"row",
            justifyContent:"space-around",
            alignItems:"center",
            margin:"1%",
            background:"beige"
        }}>
            <Link to="/">
                <h1>BEWELL</h1>
            </Link>
            { !!userToken && !!userId && <button userId={userId}/> }
            <Link to="/registration" style={{
                display:"flex",
                flexDirection:"row",
                justifyContent:"flex-end"
            }}>
                <button>Registration</button>
            </Link>
        </div>
    )
}

//  variant="contained" sx={{ fontSize:"4rem" }}