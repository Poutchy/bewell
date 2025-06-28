import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Button, TextField } from "@mui/material";
import { authenticate } from "../services";

export function AuthenticationForm() {
    const [mail, setMail] = useState("")
    const navigate = useNavigate();

    const handlleSubmit = async (event) => {
        event.preventDefault();
        const data = {identifier: mail, password: "a"};
        console.log(data);
        const res = await authenticate(data);
        console.log(res);
        if(res.data.code === "E_UNDEFINED_USER"){
            navigate("/registration", {state: {mail: mail}});
        }else if(res.data.code === "E_WRONG_PASSWORD"){
            navigate("/connection", {state: {mail: mail}});
        }
    }

    return(<div className="form_div">
        <form onSubmit={handlleSubmit} className="form">
            <div className="inputs-divs">
                <TextField
                    required
                    id="authentication"
                    label="mail"
                    type="email"
                    name="authentication"
                    onChange={(e) => {
                        console.log(mail)
                        setMail(e.target.value)
                        console.log(mail)
                    }
                }
                />
            </div>
            <Button
                className="submit_button"
                variant="contained"
                color="primary"
                type="submit"
            >Login</Button>
        </form>
    </div>)
}
