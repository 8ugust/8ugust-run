import {BrowserRouter, Routes, Route} from 'react-router-dom';
import { createContext, useState } from 'react';
import Login from './page/Login';
import Home from './page/Home';
import React from 'react';
import './App.css';
import Header from './components/Header';

// Create Context For Global Variable
export const Context = createContext();

// Define Default Global Value
const initialGlobal = {
	onLoad: false
}

function App() {

	// Global Variable
	const [global, setVariable] = useState(initialGlobal);

	// Change Function Global Variable
	const setGlobal = (k, v) => {
		let isExist = false; // Check Exist Key
		Object.keys(global).forEach(item => {
			if (item === k) isExist = true;
		}); 
		
		if (!isExist) return false;
		setVariable({...global, [k]: v});
	}

	return (
		<div className="App">
			<div className='body-wrap'>
				<Context.Provider value={{global, setGlobal}}>
					<BrowserRouter>
						<Header />
						<Routes>
							<Route path='/' element={<Login />}></Route>
							<Route path='/home' element={<Home />}></Route>
						</Routes>
					</BrowserRouter>
				</Context.Provider>
			</div>	
			
		</div>
	);
}

export default App;
