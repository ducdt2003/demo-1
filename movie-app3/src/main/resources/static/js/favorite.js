const API_URL = "/api/favorites";

// 🟥 Thêm phim vào danh sách yêu thích
async function addFavorite(userId, movieId) {
    try {
        const response = await fetch(API_URL, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ userId, movieId })
        });

        if (!response.ok) {
            throw new Error(`Lỗi API: ${response.status} - ${response.statusText}`);
        }

        alert("Thêm phim yêu thích thành công!");
        console.log("Đã thêm:", movieId);

        // Cập nhật giao diện nút yêu thích
        const button = document.querySelector(`[data-movieId='${movieId}']`);
        if (button) {
            button.classList.add("text-danger");
            button.innerHTML = '<i class="fas fa-heart"></i> Đã yêu thích';
            button.disabled = true;
        }

    } catch (error) {
        console.error("Lỗi:", error);
        alert("Có lỗi xảy ra khi thêm phim yêu thích!");
    }
}

// 🟦 Lấy danh sách phim yêu thích của người dùng
async function getFavorites(userId) {
    try {
        const response = await fetch(`${API_URL}/movies?userId=${userId}`);

        if (!response.ok) throw new Error("Không thể lấy danh sách phim yêu thích.");

        const favorites = await response.json();
        console.log("Danh sách phim yêu thích:", favorites);
        return favorites;

    } catch (error) {
        console.error("Lỗi:", error);
        return [];
    }
}

// 🟧 Xóa một phim yêu thích
async function removeFavorite(userId, movieId) {
    try {
        const response = await fetch(`${API_URL}?userId=${userId}&movieId=${movieId}`, {
            method: "DELETE",
            headers: { "Content-Type": "application/json" }
        });

        if (!response.ok) throw new Error("Không thể xóa phim yêu thích.");

        alert("Xóa phim yêu thích thành công!");
        console.log("Đã xóa phim:", movieId);

        // Xóa khỏi danh sách hiển thị
        const movieItem = document.querySelector(`[data-movieId='${movieId}']`).closest(".movie-item");
        if (movieItem) {
            movieItem.remove();
        }

    } catch (error) {
        console.error("Lỗi:", error);
        alert("Có lỗi xảy ra khi xóa phim yêu thích!");
    }
}

// 🟥 Xóa toàn bộ phim yêu thích của người dùng
async function removeAllFavorites(userId) {
    try {
        const response = await fetch(`${API_URL}/${userId}/all`, {
            method: "DELETE",
            headers: { "Content-Type": "application/json" }
        });

        if (!response.ok) throw new Error("Không thể xóa toàn bộ phim yêu thích.");

        alert("Xóa toàn bộ phim yêu thích thành công!");
        console.log("Đã xóa tất cả phim yêu thích của user:", userId);

        // Xóa tất cả phần tử khỏi giao diện
        document.querySelectorAll(".movie-item").forEach(item => item.remove());

    } catch (error) {
        console.error("Lỗi:", error);
        alert("Có lỗi xảy ra khi xóa tất cả phim yêu thích!");
    }
}

// 🟦 Xử lý sự kiện thêm & xóa phim yêu thích
document.addEventListener("DOMContentLoaded", function () {
    // Xử lý khi nhấn nút "Thêm vào yêu thích"
    document.querySelectorAll(".btn-add-favorite").forEach(button => {
        button.addEventListener("click", function () {
            const userId = "1"; // User ID mặc định là 1
            const movieId = this.getAttribute("data-movieId");

            addFavorite(userId, movieId);
        });
    });

    // Xử lý khi nhấn nút "Xóa khỏi yêu thích"
    document.querySelectorAll(".btn-remove-favorite").forEach(button => {
        button.addEventListener("click", function (event) {
            event.preventDefault();

            const userId = this.getAttribute("data-userId");
            const movieId = this.getAttribute("data-movieId");

            removeFavorite(userId, movieId);
        });
    });
});
