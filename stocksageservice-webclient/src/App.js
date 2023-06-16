import hatLogo from './hatLogo.svg';
import './App.css';

function App() {
  const [selected, setSelected] = useState(

    )

  const navItem = [
    {title: "Historical Data", isSelected: true},
    {title: "Recent Requests", isSelected: false},
    {title: "Saved Requests", isSelected: false}
  ]

  
  return (
    <div className="App">
      <header className="header">
        <img src={hatLogo} className="logo" alt="logo"/>
        <div className="loginInitial">G</div>
      </header>
    </div>
  );
}

export default App;
