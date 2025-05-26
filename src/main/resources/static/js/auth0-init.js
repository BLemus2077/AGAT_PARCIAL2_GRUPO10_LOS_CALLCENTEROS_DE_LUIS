async function initializeToken() {
  if (sessionStorage.getItem("access_token")) return;

  try {
    const res = await fetch("/api/user-token", { credentials: "include" });
    if (!res.ok) throw new Error("No autorizado");

    const token = await res.text();
    sessionStorage.setItem("access_token", token);
  } catch (err) {
    console.error("No se pudo obtener el token", err);
    window.location.href = "/login"; 
  }
}
