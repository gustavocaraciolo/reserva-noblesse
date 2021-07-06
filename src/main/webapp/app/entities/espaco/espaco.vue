<template>
  <div>
    <h2 id="page-heading" data-cy="EspacoHeading">
      <span v-text="$t('reservaNoblesseApp.espaco.home.title')" id="espaco-heading">Espacos</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('reservaNoblesseApp.espaco.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'EspacoCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-espaco"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('reservaNoblesseApp.espaco.home.createLabel')"> Create a new Espaco </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && espacos && espacos.length === 0">
      <span v-text="$t('reservaNoblesseApp.espaco.home.notFound')">No espacos found</span>
    </div>
    <div class="table-responsive" v-if="espacos && espacos.length > 0">
      <table class="table table-striped" aria-describedby="espacos">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('nome')">
              <span v-text="$t('reservaNoblesseApp.espaco.nome')">Nome</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'nome'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('reserva.id')">
              <span v-text="$t('reservaNoblesseApp.espaco.reserva')">Reserva</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'reserva.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="espaco in espacos" :key="espaco.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'EspacoView', params: { espacoId: espaco.id } }">{{ espaco.id }}</router-link>
            </td>
            <td>{{ espaco.nome }}</td>
            <td>
              <div v-if="espaco.reserva">
                <router-link :to="{ name: 'ReservaView', params: { reservaId: espaco.reserva.id } }">{{ espaco.reserva.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'EspacoView', params: { espacoId: espaco.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'EspacoEdit', params: { espacoId: espaco.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(espaco)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="reservaNoblesseApp.espaco.delete.question" data-cy="espacoDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-espaco-heading" v-text="$t('reservaNoblesseApp.espaco.delete.question', { id: removeId })">
          Are you sure you want to delete this Espaco?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-espaco"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeEspaco()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="espacos && espacos.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./espaco.component.ts"></script>
