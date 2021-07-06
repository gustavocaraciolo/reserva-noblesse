/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ApartamentoDetailComponent from '@/entities/apartamento/apartamento-details.vue';
import ApartamentoClass from '@/entities/apartamento/apartamento-details.component';
import ApartamentoService from '@/entities/apartamento/apartamento.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Apartamento Management Detail Component', () => {
    let wrapper: Wrapper<ApartamentoClass>;
    let comp: ApartamentoClass;
    let apartamentoServiceStub: SinonStubbedInstance<ApartamentoService>;

    beforeEach(() => {
      apartamentoServiceStub = sinon.createStubInstance<ApartamentoService>(ApartamentoService);

      wrapper = shallowMount<ApartamentoClass>(ApartamentoDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { apartamentoService: () => apartamentoServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundApartamento = { id: 123 };
        apartamentoServiceStub.find.resolves(foundApartamento);

        // WHEN
        comp.retrieveApartamento(123);
        await comp.$nextTick();

        // THEN
        expect(comp.apartamento).toBe(foundApartamento);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundApartamento = { id: 123 };
        apartamentoServiceStub.find.resolves(foundApartamento);

        // WHEN
        comp.beforeRouteEnter({ params: { apartamentoId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.apartamento).toBe(foundApartamento);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
