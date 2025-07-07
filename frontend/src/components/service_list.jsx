import React, {useState} from "react";
import { Link } from "react-router-dom"
import {Box, Button, Modal, Typography} from "@mui/material";

export function ServiceList(props) {
    const [open, setOpen] = useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);

    const salon = props.salon;
    const service = props.service;

    return (
        <div>
            <div
                className="salon-list-item"
                onClick={handleOpen}>{service.name} - {service.price}
            </div>
            <Modal
                open={open}
                onClose={handleClose}
                aria-labelledby="modal-modal-title"
                aria-describedby="modal-modal-description"
            >
                <Box className="modal-box" sx={
                    {
                        position: 'absolute',
                        top: '50%',
                        left: '50%',
                        transform: 'translate(-50%, -50%)',
                        width: 400,
                        bgcolor: 'background.paper',
                        border: '2px solid #000',
                        boxShadow: 24,
                        p: 4,
                    }
                }>
                    <div>
                        <Typography id="modal-modal-title" variant="h6" component="h2">
                            {service.name}
                        </Typography>
                    </div>
                    <Typography id="modal-modal-description" sx={{ mt: 2 }}>
                        {service.description}
                    </Typography>
                    <Link to="/reservation" state={{salon, service}}>
                        <Button>Make your reservation</Button>
                    </Link>
                </Box>
            </Modal>
        </div>
    );
}
