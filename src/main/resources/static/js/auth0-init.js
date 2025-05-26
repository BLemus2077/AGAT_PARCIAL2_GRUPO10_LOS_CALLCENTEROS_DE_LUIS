async function initializeAuth0() {
  if (sessionStorage.getItem("access_token")) return;

  const res = await fetch("/api/config/auth0");
  const config = await res.json();

  const auth0Client = await createAuth0Client({
    domain: config.domain,
    client_id: config.clientId,
    audience: config.audience,
    useRefreshTokens: true,
    cacheLocation: "sessionstorage"
  });

  const token = await auth0Client.getTokenSilently();
  sessionStorage.setItem("access_token", token);
}

