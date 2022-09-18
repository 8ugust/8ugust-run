import axios from "axios";
import { useState } from "react";
import {BiUserCircle} from 'react-icons/bi';


function Login() {
    const borderWhite = '2px solid white';
    const [signup, setSignup] = useState(false);
    const [account, setAccount] = useState({
        id: ['', 'Y', borderWhite],
        password: ['', 'Y', borderWhite],
        confirm: ['', 'Y', borderWhite],
        name: ['', 'Y', borderWhite],
        phone: ['', 'Y', borderWhite],
        birth: ['', 'Y', borderWhite],
        gender: ['', 'Y', borderWhite]
    });

    const onChnage = (event, type=null) => {
        if (type === null) {
            setAccount({
                ...account,
                [event.target.name]: [event.target.value, 'Y', borderWhite]
            });
        }

        if (type === 'reset') {
            setAccount({
                id: ['', 'Y', borderWhite],
                password: ['', 'Y', borderWhite],
                confirm: ['', 'Y', borderWhite],
                name: ['', 'Y', borderWhite],
                phone: ['', 'Y', borderWhite],
                birth: ['', 'Y', borderWhite],
                gender: ['', 'Y', borderWhite]
            })
        }
    };

    const clickSignIn = () => {
		console.log("Request");
		axios.post("/api/user/test")
			.then(response => console.log(response))
			.catch(error => console.log(error));
	};

    const clickSignUp = () => {
        // Empty Check
        for (const item of Object.keys(account)) {
            if (account[item][0] === '') {
                alert(item + "을(를) 입력해주세요.");
                setAccount({...account, [item]:['', 'N', '2px solid red']});
                return false;
            }
        }

        // Password Check
        if (account.password[0] !== account.confirm[0]) {
            alert('비밀번호가 일치하지 않습니다.');
            setAccount({...account, ['confirm']:['', 'N', '2px solid red']});
            return false;
        }

        const formData = new FormData();
        formData.append('id', account.id[0]);
        formData.append('password', account.password[0]);
        formData.append('name', account.name[0]);
        formData.append('phone', account.phone[0]);
        formData.append('gender', account.gender[0]);
        formData.append('birth', account.birth[0]);

        axios.post("/signUp", formData, {headers: {'Content-Type':'mulipart/form-data'}})
        .then(response => {
            if (response.status === 200) {
                alert('회원가입이 완료되었습니다.');
                onChnage('', 'reset');
                setSignup(false);
            }
        })
        .catch(error => {
            console.log(error)
            alert('서버에서 오류가 발생했습니다.');
        })
    };

    return (
        <>
        <div>
            <div style={{paddingTop:'100px'}}></div>
            <BiUserCircle size='5em' color='white'/>
            <div className='header'>LOG IN</div>
            <div className="sign-in-wrap" style={{visibility:(signup === false ? 'visible' : 'hidden'), opacity:(signup === false ? 100 : 0)}}>
                <input className='input_type' type={"text"} value={account.id[0]} name={"id"} placeholder={"e-mail"} onChange={onChnage} style={{border:account.id[2]}}></input>
                <input className='input_type' type={"password"} value={account.password[0]} name={"password"} placeholder={"password"} onChange={onChnage} style={{border:account.password[2]}}></input>
                <input className="button_type" type={"button"} value={"확인"} onClick={() => clickSignIn()}></input>
                <input className="button_type" type={"button"} value={"회원가입"} onClick={() => setSignup(true)}></input>
            </div>
            <div className='sign-up-wrap' style={{visibility:(signup === true ? 'visible' : 'hidden'), opacity:(signup === false ? 0 : 100)}}>
                <input className='input_type' type={"text"} value={account.id[0]} name={"id"} placeholder={"e-mail"} onChange={onChnage} style={{border:account.id[2]}}></input>
                <input className='input_type' type={"password"} value={account.password[0]} name={"password"} placeholder={"password"} onChange={onChnage} style={{border:account.password[2]}}></input>
                <input className='input_type' type={"password"} value={account.confirm[0]} name={"confirm"} placeholder={"password confirm"} onChange={onChnage} style={{border:account.confirm[2]}}></input>
                <input className='input_type' type={"text"} value={account.name[0]} name={"name"} placeholder={"name"} onChange={onChnage} style={{border:account.name[2]}}></input>
                <input className='input_type' type={"text"} value={account.phone[0]} name={"phone"} placeholder={"phone"} onChange={onChnage} style={{border:account.phone[2]}}></input>
                <input className='input_type' type={"text"} value={account.birth[0]} name={"birth"} placeholder={"birth"} onChange={onChnage} style={{border:account.birth[2]}}></input>
                <div>
                        <select className='select-type' placeholder={"gender"} name={"gender"} onChange={onChnage} style={{border:account.gender[2]}}>
                            <option defaultValue={"gender"} hidden>Gender</option>
                            <option value={"M"}>Male</option>
                            <option value={"F"}>Femail</option>
                        </select>
                </div>  
                <input className="button_type" type={"button"} value={"계속"} onClick={() => clickSignUp()}></input>
                <input className="button_type" type={"button"} value={"취소"} onClick={() => {setSignup(false); onChnage('', 'reset')}}></input>
            </div>
        </div>
        </>
    )
}

export default Login;