package me.maxinne.testeboticario

import org.springframework.stereotype.Service

@Service
class DummyDataSource() {
    private val data = mutableListOf(
        DummyData(
            id = "FEONCB",
            name = "Fazemos os olhos dos nossos clientes brilharem",
            values = listOf("FOCO NO CLIENTE")
        ),
        DummyData(
            id = "SI",
            name = "Somos inquietos",
            values = listOf(
                "AUTODESENVOLVIMENTO",
                "EMPREENDEDORISMO",
                "ADAPTABILIDADE"
            )
        ),
        DummyData(
            id = "NNR",
            name = "Nutrimos nossas relações",
            values = listOf(
                "CUIDADO E RESPEITO",
                "INFLUÊNCIA MULTIFUNCIONAL E DESENVOLVIMENTO",
                "GESTÃO DE PESSOAS"
            )
        ),
        DummyData(
            id = "SAPE",
            name = "Somos apaixonados pela execução",
            values = listOf(
                "FOCO EM EXECUÇÃO",
                "AUTONOMIA",
                "RESOLUÇÃO DE PROBLEMAS"
            )
        ),
        DummyData(
            id = "BSR",
            name = "Buscamos sucesso responsável",
            values = listOf(
                "VISÃO DE NEGÓCIO",
                "RESPONSABILIDADE FINANCEIRA"
            )
        )
    )

    fun getData(): List<DummyShortData> {
        return data.map { data: DummyData -> DummyShortData(data.id, data.name) }
    }

    fun getDataById(id: String): DummyData {
        return data.first { data -> data.id == id }
    }
}

data class DummyShortData(val id: String, val name: String)
data class DummyData(val id: String, val name: String, val values: List<String>?)