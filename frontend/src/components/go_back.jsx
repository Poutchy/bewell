import { Button } from "@mui/material";
import React from "react";
import { useNavigate } from "react-router-dom";

export function GoBack(){
    const navigate = useNavigate();

    const ret = () => {
        navigate(-1);
    }

    return(<div className="go_back_button">
        <Button
            variant="contained"
            color="primary"
            onClick={ ret }
        >return</Button>
    </div>)
}
