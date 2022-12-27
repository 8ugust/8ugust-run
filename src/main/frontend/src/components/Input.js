import { Box, TextField } from "@mui/material";


const Input = () => {

    return (<>
        <Box sx={{background:'white', borderRadius:'20px', marginBottom: '20px'}}>
            <TextField size='small' sx={{'& fieldset':{borderRadius: '20px'}, width:'100%'}} label='Email' />
        </Box>
    </>)
}

export default Input;