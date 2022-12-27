import { Button } from "@mui/material";
import { Box } from "@mui/material";

const MuiButton = () => {

    const style_list = {
        borderRadius:'30px', 
        fontWeight:'bold', 
        fontSize:'15px', 
        color:'white',
        border:'0px'
    };

    return (<>
        <Box style={{paddingBottom:'10px'}}>
            <Button className='button-mui' style={style_list}>LOGIN</Button>
        </Box>
    </>)
}

export default MuiButton;