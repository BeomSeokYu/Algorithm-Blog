const token = searchParam('token');

if (token) {
    localStorage.setItem("access_token", token);
    clearTokenParam();
}

let navLoginUl = document.getElementById('nav-login-ul');
let registerBtnDiv = document.getElementById('register-btn-div');
let modDelDiv = document.getElementById('mod-del-div');
renderNav();
refreshAccessTokenIfNeeded().then(() => renderNav());

function searchParam(key) {
    return new URLSearchParams(location.search).get(key);
}

function clearTokenParam() {
    const url = new URL(window.location.href);
    if (url.searchParams.has('token')) {
        url.searchParams.delete('token');
        history.replaceState({}, document.title, url.pathname + url.search);
    }
}

function renderNav() {
    if (localStorage.getItem("access_token") == null) {
        navLoginUl.innerHTML = '<li class="nav-item"><a href="/login" class="nav-link link-body-emphasis px-2">Login</a></li>';
        if (registerBtnDiv != null) {
            registerBtnDiv.innerHTML = '';
        }
        if (modDelDiv != null) {
            modDelDiv.innerHTML = '';
        }
    } else {
        navLoginUl.innerHTML = '<li class="nav-item"><button class="nav-link link-body-emphasis px-2" onclick="logout()">Logout</button></li>';
    }
}

function refreshAccessTokenIfNeeded() {
    if (localStorage.getItem("access_token") != null) {
        return Promise.resolve();
    }
    return fetch('/api/token', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then((response) => {
            if (!response.ok) {
                return null;
            }
            return response.json();
        })
        .then((result) => {
            if (result && result.accessToken) {
                localStorage.setItem('access_token', result.accessToken);
            }
        })
        .catch(() => null);
}

function logout() {
    localStorage.removeItem('access_token');
    location.replace('/logout')
}
