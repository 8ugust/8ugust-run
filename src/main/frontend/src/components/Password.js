import { FormControl, IconButton, InputAdornment, InputLabel, OutlinedInput, Box } from "@mui/material";
import { Visibility, VisibilityOff } from "@mui/icons-material";
import { useNavigate } from "react-router-dom";
import { useState } from "react";

const Password = ({id, value, fnChange, showFg=false, isCheck=false, req=false}) => {

    const navigate = useNavigate();
    const [showPassword, setShowPassword] = useState(false);
    const handleClickShowPassword = () => setShowPassword((show) => !show);
    const handleMouseDownPassword = (event) => {
        event.preventDefault();
    };

    const styled_component = {
        display:(showFg) ? 'block' : 'none',
        paddingRight: '10px',
        float: 'right',
        color: 'gray',
    }


    return (<>
        <Box sx={{background:'white', borderRadius:'20px', marginBottom: (showFg) ? '40px' : '20px'}}>
            <FormControl size='small' sx={{'& fieldset':{borderRadius: '20px'}, width: '100%'}}>
                <InputLabel htmlFor="outlined-adornment-password"  required={req}>{(isCheck) ? 'Password Check' : 'Password'}</InputLabel>
                <OutlinedInput
                    id={id} value={value} onChange={(v) => fnChange(v.target.value)}
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
            <div style={styled_component} onClick={() => navigate('/Forgot-Password')}>Forgot Your Password</div>
        </Box>
    </>);
}

export default Password;