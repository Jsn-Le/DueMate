import './App.css'

function App() {
    return (
        <main>
            <div className="login-container">
                <div className="branding">
                    <h1>D U E M A T E</h1>
                    <h4>Manage your bills.</h4>
                    <h4>Stay ahead of due dates.</h4>
                </div>

                <div className="login-form">
                    <input type="email" placeholder="Email" />
                    <input type="password" placeholder="Password" />
                    <button>Login</button>
                </div>

                <div className="account-links">
                    <h5>Forgot Password?</h5>
                    <h5>Don't have an account? Register</h5>
                </div>
            </div>

        </main>
    )
}

export default App
