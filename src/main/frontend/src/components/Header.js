import Container from '@material-ui/core/Container';
import Toolbar from '@material-ui/core/Toolbar';
import { useLocation } from "react-router-dom";
import AppBar from '@material-ui/core/AppBar';
import { useState, useEffect } from "react";
import Loading from "./Loading";

const Header = () => {
    const [hide, setHide] = useState(false);
    const location = useLocation();

    const getPath = () => {
        const path = location.pathname; 
        if (path === '/') setHide(true);
    }; useEffect(getPath, [location])

    return (
        <>
            <Loading />
            <AppBar position="static">
                <Container maxWidth="xl">
                    <Toolbar disableGutters style={{minHeight:(hide ? '0px' : '64px')}}>
                    </Toolbar>
                </Container>
            </AppBar>
        </>
    )
}

export default Header;