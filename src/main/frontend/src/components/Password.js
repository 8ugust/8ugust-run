import { FormControl, IconButton, InputAdornment, InputLabel, OutlinedInput, Box } from "@mui/material";
import { Visibility, VisibilityOff } from "@mui/icons-material";
import { useNavigate } from "react-router-dom";
import { useState } from "react";

const Password = () => {

    const [showPassword, setShowPassword] = useState(false);
    const handleClickShowPassword = () => setShowPassword((show) => !show);
    const handleMouseDownPassword = (event) => {
        event.preventDefault();
    };

    const navigate = useNavigate();

    return (<>
        <Box sx={{background:'white', borderRadius:'20px', marginBottom: '40px'}}>
            <FormControl size='small' sx={{'& fieldset':{borderRadius: '20px'}, width: '100%'}}>
                <InputLabel htmlFor="outlined-adornment-password">Password</InputLabel>
                <OutlinedInput
                    id="outlined-adornment-password"
                    type={showPassword ? 'text' : 'password'}
                    endAdornment={
                        <InputAdornment position="end">
                            <IconButton
                            aria-label="toggle password visibility"
                            onClick={handleClickShowPassword}
                            onMouseDown={handleMouseDownPassword}
                            edge="end"
                            >
                            {showPassword ? <VisibilityOff /> : <Visibility />}
                            </IconButton>
                        </InputAdornment>
                    }
                    label="Password"
                />
            </FormControl>
            <div style={{float:'right', paddingRight:'10px', color:'gray'}} onClick={() => navigate('/Forgot-Password')}>Forgot Your Password</div>
        </Box>
    </>);
}

export default Password;