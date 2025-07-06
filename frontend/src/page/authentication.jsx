import React, { useEffect, useState } from "react";
import { useNavigate, Navigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";
import axios from "axios";

import {
    Box,
    Button,
    Container,
    Divider,
    TextField,
    Typography,
    Alert,
    Avatar,
    CircularProgress,
} from "@mui/material";

import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import GoogleIcon from "@mui/icons-material/Google";
import {useUpdateContext} from "../interface";

export function Authentication() {
    const navigate = useNavigate();
    const [user, setUser] = useState(null);
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState(null);
    const [redirectAfterLogin, setRedirectAfterLogin] = useState(false);

    const updateContext = useUpdateContext();

    useEffect(() => {
        /* global google */
        if (window.google) {
            google.accounts.id.initialize({
                client_id:
                    "542683349275-jat69qal4s7bo8jhduj86teln4duuir2.apps.googleusercontent.com",
                callback: handleCallbackResponse,
            });

            // Optionally show One Tap prompt once on mount
            google.accounts.id.prompt();
        }
    }, []);

    async function handleCallbackResponse(response) {
        try {
            const res = await axios.post("http://localhost:8081/api/auth/google-signin", {
                credential: response.credential,
            });

            const backendToken = res.data.token || res.data;
            localStorage.setItem("jwt", backendToken);

            const userObject = jwtDecode(response.credential);
            setUser(userObject);

            navigate("/reservation");
        } catch (err) {
            console.error("Backend verification failed", err);
            alert("Google login failed. Please try again.");
        }
    }

    if (redirectAfterLogin) {
        return <Navigate to="/reservation" replace />;
    }


    async function handleEmailPasswordSignIn(e) {
        e.preventDefault();
        setError(null);

        try {
            const res = await axios.post("http://localhost:8081/api/auth/signin", {
                username: email,
                password: password,
            });

            const { token, role } = res.data;

            await updateContext({ token, role });

            // Redirect immediately after context update
            navigate("/reservation", { replace: true });

            // Save to localStorage for refresh safety
            //localStorage.setItem("jwt", token);
            //localStorage.setItem("role", role);

            // Now, let the redirect happen after context update
            setRedirectAfterLogin(true);
        } catch (err) {
            console.error(err);
            setError("Invalid username or password");
        }
    }

    // Simplified Google sign-in button click handler
    function handleGoogleSignInClick() {
        if (window.google) {
            google.accounts.id.prompt();
        }
    }

    return (
        <Container maxWidth="xs" sx={{ mt: 8, mb: 8 }}>
            <Box
                sx={{
                    display: "flex",
                    flexDirection: "column",
                    alignItems: "center",
                    p: 4,
                    boxShadow: 3,
                    borderRadius: 2,
                    bgcolor: "background.paper",
                }}
            >
                <Avatar sx={{ m: 1, bgcolor: "secondary.main" }}>
                    <LockOutlinedIcon />
                </Avatar>

                <Typography component="h1" variant="h5" gutterBottom>
                    Sign In
                </Typography>

                {/* Removed hidden Google button */}

                {/* Custom white Google button */}
                <Button
                    variant="outlined"
                    startIcon={<GoogleIcon />}
                    onClick={handleGoogleSignInClick}
                    sx={{
                        mb: 2,
                        bgcolor: "#fff",
                        color: "rgba(0,0,0,0.54)",
                        borderColor: "rgba(0,0,0,0.23)",
                        textTransform: "none",
                        fontWeight: "bold",
                        "&:hover": {
                            bgcolor: "#eee",
                            borderColor: "rgba(0,0,0,0.3)",
                        },
                        width: "100%",
                    }}
                >
                    Sign in with Google
                </Button>

                <Divider sx={{ width: "100%", my: 2 }}>OR</Divider>

                <Box
                    component="form"
                    onSubmit={handleEmailPasswordSignIn}
                    sx={{ width: "100%" }}
                    noValidate
                >
                    <TextField
                        margin="normal"
                        required
                        fullWidth
                        id="email"
                        label="Email Address"
                        type="email"
                        autoComplete="email"
                        autoFocus
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />

                    <TextField
                        margin="normal"
                        required
                        fullWidth
                        id="password"
                        label="Password"
                        type="password"
                        autoComplete="current-password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />

                    {error && (
                        <Alert severity="error" sx={{ mt: 2 }}>
                            {error}
                        </Alert>
                    )}

                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        sx={{ mt: 3, mb: 2 }}
                    >
                        {"Sign In"}
                    </Button>
                </Box>

                {user && (
                    <Box sx={{ mt: 3, textAlign: "center" }}>
                        <Typography variant="h6">Welcome, {user.name}</Typography>
                        <Avatar
                            alt={user.name}
                            src={user.picture}
                            sx={{ width: 80, height: 80, mx: "auto", mt: 1, mb: 1 }}
                        />
                        <Typography variant="body1">{user.email}</Typography>
                    </Box>
                )}
            </Box>
        </Container>
    );
}