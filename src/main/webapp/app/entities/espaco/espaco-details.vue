<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="espaco">
        <h2 class="jh-entity-heading" data-cy="espacoDetailsHeading">
          <span v-text="$t('reservaNoblesseApp.espaco.detail.title')">Espaco</span> {{ espaco.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('reservaNoblesseApp.espaco.nome')">Nome</span>
          </dt>
          <dd>
            <span>{{ espaco.nome }}</span>
          </dd>
          <dt>
            <span v-text="$t('reservaNoblesseApp.espaco.reserva')">Reserva</span>
          </dt>
          <dd>
            <div v-if="espaco.reserva">
              <router-link :to="{ name: 'ReservaView', params: { reservaId: espaco.reserva.id } }">{{ espaco.reserva.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="espaco.id" :to="{ name: 'EspacoEdit', params: { espacoId: espaco.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./espaco-details.component.ts"></script>
