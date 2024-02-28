import React, {useState} from 'react';
import {useNavigate, useRoutes} from 'react-router-dom';
import './App.css';
import routes from "./routes";
import {UserContext, UserData} from "./auth/UserContext";

const useUserInTab = (): UserData => {

  const [basicAuth, setBasicAuth] = useState<string>();
  const [username, setUsername] = useState<string>();
  const navigate = useNavigate();

  const login = (newUsername: string, password: string) => {
    setUsername(newUsername);
    setBasicAuth('Basic ' + btoa(`${newUsername}:${password}`));
    navigate('/');
  };

  const logout = () => {
    setUsername(undefined);
    setBasicAuth(undefined);
    navigate('/login');
  }

  return {
    username,
    basicAuth,
    login,
    logout
  }
};

function App() {
  const content = useRoutes(routes);
  const user = useUserInTab()

  return (
    <UserContext.Provider value={user}>
      {content}
    </UserContext.Provider>
  );
}

export default App;
