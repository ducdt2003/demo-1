const API_URL = "/api/favorites";

// Thêm phim vào danh sách yêu thích
async function addFavorite(userId, movieId) {
    try {
        const response = await fetch(`${API_URL}/add`, {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: new URLSearchParams({ userId, movieId })
        });

        if (!response.ok) throw new Error("Không thể thêm phim yêu thích.");

        const favorite = await response.json();
        console.log("Đã thêm:", favorite);
        alert("Thêm phim yêu thích thành công!");
    } catch (error) {
        console.error(error);
        alert("Có lỗi xảy ra!");
    }
}

// Lấy danh sách phim yêu thích
async function getFavorites(userId) {
    try {
        const response = await fetch(`${API_URL}/${userId}`);

        if (!response.ok) throw new Error("Không thể lấy danh sách phim.");

        const favorites = await response.json();
        console.log("Danh sách phim yêu thích:", favorites);
        return favorites;
    } catch (error) {
        console.error(error);
        return [];
    }
}

// Xóa một phim yêu thích
async function removeFavorite(userId, movieId) {
    try {
        const response = await fetch(`${API_URL}/remove?userId=${userId}&movieId=${movieId}`, {
            method: "DELETE"
        });

        if (!response.ok) throw new Error("Không thể xóa phim yêu thích.");

        console.log("Đã xóa phim:", movieId);
        alert("Xóa phim yêu thích thành công!");
    } catch (error) {
        console.error(error);
        alert("Có lỗi xảy ra!");
    }
}

// Xóa tất cả phim yêu thích của người dùng
async function removeAllFavorites(userId) {
    try {
        const response = await fetch(`${API_URL}/removeAll/${userId}`, {
            method: "DELETE"
        });

        if (!response.ok) throw new Error("Không thể xóa toàn bộ phim yêu thích.");

        console.log("Đã xóa tất cả phim yêu thích của user:", userId);
        alert("Xóa toàn bộ phim yêu thích thành công!");
    } catch (error) {
        console.error(error);
        alert("Có lỗi xảy ra!");
    }
}